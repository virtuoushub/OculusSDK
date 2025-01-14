/************************************************************************************

Filename    :   CAPI_D3D1X_DistortionRenderer.cpp
Content     :   Experimental distortion renderer
Created     :   March 7th, 2014
Authors     :   Tom Heath

Copyright   :   Copyright 2014 Oculus VR, Inc. All Rights reserved.

Licensed under the Oculus VR Rift SDK License Version 3.1 (the "License"); 
you may not use the Oculus VR Rift SDK except in compliance with the License, 
which is provided at the time of installation or download, or which 
otherwise accompanies this software in either electronic or hard copy form.

You may obtain a copy of the License at

http://www.oculusvr.com/licenses/LICENSE-3.1 

Unless required by applicable law or agreed to in writing, the Oculus VR SDK 
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

************************************************************************************/
#include "CAPI_D3D9_DistortionRenderer.h"
#define OVR_D3D_VERSION 9
#include "OVR_CAPI_D3D.h"

namespace OVR { namespace CAPI { namespace D3D9 {


///QUESTION : Why not just a normal constructor?
CAPI::DistortionRenderer* DistortionRenderer::Create(ovrHmd hmd,
                                                     FrameTimeManager& timeManager,
                                                     const HMDRenderState& renderState)
{
    return new DistortionRenderer(hmd, timeManager, renderState);
}

DistortionRenderer::DistortionRenderer(ovrHmd hmd, FrameTimeManager& timeManager,
                                       const HMDRenderState& renderState)
    : CAPI::DistortionRenderer(ovrRenderAPI_D3D9, hmd, timeManager, renderState) 
{
}
/**********************************************/
DistortionRenderer::~DistortionRenderer()
{
	//Release any memory 
	eachEye[0].dxIndices->Release();
	eachEye[0].dxVerts->Release();
	eachEye[1].dxIndices->Release();
	eachEye[1].dxVerts->Release();
}


/******************************************************************************/
bool DistortionRenderer::Initialize(const ovrRenderAPIConfig* apiConfig,
                                    unsigned arg_distortionCaps)
{
	///QUESTION - what is returned bool for???  Are we happy with this true, if not config.
    const ovrD3D9Config * config = (const ovrD3D9Config*)apiConfig;
    if (!config)                return true; 
    if (!config->D3D9.pDevice)  return false;

	//Glean all the required variables from the input structures
	device         = config->D3D9.pDevice;
    swapChain      = config->D3D9.pSwapChain;
	screenSize     = config->D3D9.Header.RTSize;
	distortionCaps = arg_distortionCaps;

	GfxState = *new GraphicsState(device);

	CreateVertexDeclaration();
	CreateDistortionShaders();
	Create_Distortion_Models();

    return true;
}


/**************************************************************/
void DistortionRenderer::SubmitEye(int eyeId, ovrTexture* eyeTexture)
{
	//Doesn't do a lot in here??
	const ovrD3D9Texture* tex = (const ovrD3D9Texture*)eyeTexture;

	//Write in values
    eachEye[eyeId].texture = tex->D3D9.pTexture;

	// Its only at this point we discover what the viewport of the texture is.
	// because presumably we allow users to realtime adjust the resolution.
    eachEye[eyeId].TextureSize    = tex->D3D9.Header.TextureSize;
    eachEye[eyeId].RenderViewport = tex->D3D9.Header.RenderViewport;

    const ovrEyeRenderDesc& erd = RState.EyeRenderDesc[eyeId];
    
    ovrHmd_GetRenderScaleAndOffset( erd.Fov,
                                    eachEye[eyeId].TextureSize, eachEye[eyeId].RenderViewport,
                                    eachEye[eyeId].UVScaleOffset );
}


/******************************************************************/
void DistortionRenderer::EndFrame(unsigned char* latencyTesterDrawColor, unsigned char* latencyTester2DrawColor)
{
	OVR_UNUSED(latencyTesterDrawColor);

	///QUESTION : Should I be clearing the screen? 
	///QUESTION : Should I be ensuring the screen is the render target

	if (!TimeManager.NeedDistortionTimeMeasurement())
    {
		if (RState.DistortionCaps & ovrDistortionCap_TimeWarp)
		{
			// Wait for timewarp distortion if it is time and Gpu idle
			WaitTillTimeAndFlushGpu(TimeManager.GetFrameTiming().TimewarpPointTime);
		}

        RenderBothDistortionMeshes();
    }
    else
    {
        // If needed, measure distortion time so that TimeManager can better estimate
        // latency-reducing time-warp wait timing.
        WaitUntilGpuIdle();
        double  distortionStartTime = ovr_GetTimeInSeconds();

        RenderBothDistortionMeshes();
        WaitUntilGpuIdle();

        TimeManager.AddDistortionTimeMeasurement(ovr_GetTimeInSeconds() - distortionStartTime);
    }

    if(latencyTesterDrawColor)
    {
		///QUESTION : Is this still to be supported?
        ///renderLatencyQuad(latencyTesterDrawColor);
    }

    if(latencyTester2DrawColor)
    {
        // TODO:
    }

    if (0 == (RState.DistortionCaps & ovrDistortionCap_NoSwapBuffers))
    {
        if (RState.SwapBufferCallback)
        {
            RState.SwapBufferCallback(RState.SwapBufferUserData);
        }
        else if (swapChain)
        {
            swapChain->Present(NULL, NULL, NULL, NULL, 0);
        }
        else
        {
		    device->Present( NULL, NULL, NULL, NULL );
        }

        // Force GPU to flush the scene, resulting in the lowest possible latency.
        // It's critical that this flush is *after* present.
        WaitUntilGpuIdle();
    }
}


void DistortionRenderer::WaitUntilGpuIdle()
{
	if(device)
    {
         IDirect3DQuery9* pEventQuery=NULL ;
         device->CreateQuery(D3DQUERYTYPE_EVENT, &pEventQuery) ;

         if(pEventQuery!=NULL)
         {
            pEventQuery->Issue(D3DISSUE_END) ;
            while(S_FALSE == pEventQuery->GetData(NULL, 0, D3DGETDATA_FLUSH)) ;
         }
     }
}

double DistortionRenderer::WaitTillTimeAndFlushGpu(double absTime)
{
	double       initialTime = ovr_GetTimeInSeconds();
	if (initialTime >= absTime)
		return 0.0;

	WaitUntilGpuIdle();

	double newTime   = initialTime;
	volatile int i;

	while (newTime < absTime)
	{
		for (int j = 0; j < 50; j++)
			i = 0;
		newTime = ovr_GetTimeInSeconds();
	}

	// How long we waited
	return newTime - initialTime;
}



DistortionRenderer::GraphicsState::GraphicsState(IDirect3DDevice9* d)
: device(d)
, numSavedStates(0)
{
}

void DistortionRenderer::GraphicsState::RecordAndSetState(int which, int type, DWORD newValue)
{
	SavedStateType * sst = &savedState[numSavedStates++];
	sst->which = which;
	sst->type = type;
	if (which == 0)
	{
		device->GetSamplerState(0, (D3DSAMPLERSTATETYPE)type, &sst->valueToRevertTo);
		device->SetSamplerState(0, (D3DSAMPLERSTATETYPE)type, newValue);
	}
	else
	{
		device->GetRenderState((D3DRENDERSTATETYPE)type, &sst->valueToRevertTo);
		device->SetRenderState((D3DRENDERSTATETYPE)type, newValue);
	}
}

void DistortionRenderer::GraphicsState::Save()
{
	//Record and set rasterizer and sampler states.

	numSavedStates=0;

    RecordAndSetState(0, D3DSAMP_MINFILTER,          D3DTEXF_LINEAR );
    RecordAndSetState(0, D3DSAMP_MAGFILTER,          D3DTEXF_LINEAR );
    RecordAndSetState(0, D3DSAMP_MIPFILTER,          D3DTEXF_LINEAR );
    RecordAndSetState(0, D3DSAMP_BORDERCOLOR,        0x000000 );
    RecordAndSetState(0, D3DSAMP_ADDRESSU,           D3DTADDRESS_BORDER );
    RecordAndSetState(0, D3DSAMP_ADDRESSV,           D3DTADDRESS_BORDER );

	RecordAndSetState(1, D3DRS_MULTISAMPLEANTIALIAS, FALSE );
	RecordAndSetState(1, D3DRS_DITHERENABLE,         FALSE );
	RecordAndSetState(1, D3DRS_ZENABLE,              FALSE );
    RecordAndSetState(1, D3DRS_ZWRITEENABLE,         TRUE   );
    RecordAndSetState(1, D3DRS_ZFUNC,                D3DCMP_LESSEQUAL   );
    RecordAndSetState(1, D3DRS_CULLMODE ,            D3DCULL_CCW  );
   	RecordAndSetState(1, D3DRS_ALPHABLENDENABLE ,    FALSE );
   	RecordAndSetState(1, D3DRS_DEPTHBIAS ,           0 );
    RecordAndSetState(1, D3DRS_SRCBLEND ,            D3DBLEND_SRCALPHA );
    RecordAndSetState(1, D3DRS_DESTBLEND ,           D3DBLEND_INVSRCALPHA   );
   	RecordAndSetState(1, D3DRS_FILLMODE,             D3DFILL_SOLID );
    RecordAndSetState(1, D3DRS_ALPHATESTENABLE,      FALSE);
 	RecordAndSetState(1, D3DRS_DEPTHBIAS ,           0 );
    RecordAndSetState(1, D3DRS_LIGHTING,             FALSE );
   	RecordAndSetState(1, D3DRS_FOGENABLE,            FALSE );
}


void DistortionRenderer::GraphicsState::Restore()
{
	for (int i = 0; i<numSavedStates; i++)
	{
		SavedStateType * sst = &savedState[i];
		if (sst->which == 0)
		{
			device->SetSamplerState(0, (D3DSAMPLERSTATETYPE)sst->type, sst->valueToRevertTo);
		}
		else
		{
			device->SetRenderState((D3DRENDERSTATETYPE)sst->type, sst->valueToRevertTo);
		}
	}
}


}}} // OVR::CAPI::D3D1X
