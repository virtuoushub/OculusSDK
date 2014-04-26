package com.oculusvr.capi;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import com.sun.jna.ptr.FloatByReference;

/**
 * JNA Wrapper for library <b>OVR_CAPI_GL</b><br>
 * This file was autogenerated by <a
 * href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a
 * href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few
 * opensource projects.</a>.<br>
 * For help, please visit <a
 * href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a
 * href="http://rococoa.dev.java.net/">Rococoa</a>, or <a
 * href="http://jna.dev.java.net/">JNA</a>.
 */
public interface OvrLibrary extends Library {
  public static final String JNA_LIBRARY_NAME = "C:/Users/bdavis/Git/OculusSDK/build/Bindings/C/Release/OVR_C";
  public static final NativeLibrary JNA_NATIVE_LIB = NativeLibrary.getInstance(OvrLibrary.JNA_LIBRARY_NAME);
  public static final OvrLibrary INSTANCE = (OvrLibrary) Native.loadLibrary(OvrLibrary.JNA_LIBRARY_NAME,
      OvrLibrary.class);

  public static interface ovrHmdType {
    public static final int ovrHmd_None = 0;
    public static final int ovrHmd_DK1 = 3;
    public static final int ovrHmd_DKHD = 4;
    public static final int ovrHmd_CrystalCoveProto = 5;
    public static final int ovrHmd_DK2 = 6;
    public static final int ovrHmd_Other = 7;
  };

  public static interface ovrHmdCapBits {
    public static final int ovrHmdCap_Present = 0x0001;
    public static final int ovrHmdCap_Available = 0x0002;
    public static final int ovrHmdCap_Orientation = 0x0010;
    public static final int ovrHmdCap_YawCorrection = 0x0020;
    public static final int ovrHmdCap_Position = 0x0040;
    public static final int ovrHmdCap_LowPersistence = 0x0080;
    public static final int ovrHmdCap_LatencyTest = 0x0100;
    public static final int ovrHmdCap_DynamicPrediction = 0x0200;
    public static final int ovrHmdCap_NoVSync = 0x1000;
  };

  public static interface ovrDistortionCaps {
    public static final int ovrDistortion_Chromatic = 0x01;
    public static final int ovrDistortion_TimeWarp = 0x02;
    public static final int ovrDistortion_Vignette = 0x08;
  };

  public static interface ovrEyeType {
    public static final int ovrEye_Left = 0;
    public static final int ovrEye_Right = 1;
    public static final int ovrEye_Count = 2;
  };

  public static interface ovrStatusBits {
    public static final int ovrStatus_OrientationTracked = 0x0001;
    public static final int ovrStatus_PositionTracked = 0x0002;
    public static final int ovrStatus_PositionConnected = 0x0020;
    public static final int ovrStatus_HmdConnected = 0x0080;
  };

  public static interface ovrRenderAPIType {
    public static final int ovrRenderAPI_None = 0;
    public static final int ovrRenderAPI_OpenGL = 1;
    public static final int ovrRenderAPI_Android_GLES = 2;
    public static final int ovrRenderAPI_D3D9 = 3;
    public static final int ovrRenderAPI_D3D10 = 4;
    public static final int ovrRenderAPI_D3D11 = 5;
    public static final int ovrRenderAPI_Count = 6;
  };

  public static final float OVR_DEFAULT_NECK_TO_EYE_VERTICAL = 0.12f;
  public static final String OVR_KEY_NECK_TO_EYE_VERTICAL = "NeckEyeVert";
  public static final String OVR_KEY_PLAYER_HEIGHT = "PlayerHeight";
  public static final float OVR_DEFAULT_PLAYER_HEIGHT = 1.778f;
  public static final float OVR_DEFAULT_IPD = 0.064f;
  public static final String OVR_KEY_EYE_HEIGHT = "EyeHeight";
  public static final String OVR_KEY_NECK_TO_EYE_HORIZONTAL = "NeckEyeHori";
  public static final float OVR_DEFAULT_NECK_TO_EYE_HORIZONTAL = 0.12f;
  public static final String OVR_KEY_USER = "User";
  public static final String OVR_KEY_NAME = "Name";
  public static final float OVR_DEFAULT_EYE_HEIGHT = 1.675f;
  public static final String OVR_DEFAULT_GENDER = "Male";
  public static final String OVR_KEY_GENDER = "Gender";
  public static final String OVR_KEY_IPD = "IPD";

  byte ovr_Initialize();

  void ovr_Shutdown();

  int ovrHmd_Detect();

  OvrLibrary.ovrHmd ovrHmd_Create(int index);

  @Deprecated
  void ovrHmd_Destroy(Pointer hmd);

  void ovrHmd_Destroy(OvrLibrary.ovrHmd hmd);

  OvrLibrary.ovrHmd ovrHmd_CreateDebug(int type);

  @Deprecated
  String ovrHmd_GetLastError(Pointer hmd);

  String ovrHmd_GetLastError(OvrLibrary.ovrHmd hmd);

  @Deprecated
  byte ovrHmd_StartSensor(Pointer hmd, int supportedCaps, int requiredCaps);

  byte ovrHmd_StartSensor(OvrLibrary.ovrHmd hmd, int supportedCaps, int requiredCaps);

  @Deprecated
  void ovrHmd_StopSensor(Pointer hmd);

  void ovrHmd_StopSensor(OvrLibrary.ovrHmd hmd);

  @Deprecated
  void ovrHmd_ResetSensor(Pointer hmd);

  void ovrHmd_ResetSensor(OvrLibrary.ovrHmd hmd);

  @Deprecated
  com.oculusvr.capi.SensorState.ByValue ovrHmd_GetSensorState(Pointer hmd, double absTime);

  com.oculusvr.capi.SensorState.ByValue ovrHmd_GetSensorState(OvrLibrary.ovrHmd hmd, double absTime);

  @Deprecated
  byte ovrHmd_GetSensorDesc(Pointer hmd, SensorDesc descOut);

  byte ovrHmd_GetSensorDesc(OvrLibrary.ovrHmd hmd, SensorDesc descOut);

  @Deprecated
  void ovrHmd_GetDesc(Pointer hmd, HmdDesc desc);

  void ovrHmd_GetDesc(OvrLibrary.ovrHmd hmd, HmdDesc desc);

  @Deprecated
  com.oculusvr.capi.Sizei.ByValue ovrHmd_GetFovTextureSize(Pointer hmd, int eye, com.oculusvr.capi.FovPort.ByValue fov,
      float pixelsPerDisplayPixel);

  com.oculusvr.capi.Sizei.ByValue ovrHmd_GetFovTextureSize(OvrLibrary.ovrHmd hmd, int eye,
      com.oculusvr.capi.FovPort.ByValue fov, float pixelsPerDisplayPixel);

  @Deprecated
  byte ovrHmd_ConfigureRendering(Pointer hmd, RenderAPIConfig apiConfig, int hmdCaps, int distortionCaps,
      Pointer eyeDescIn, Pointer eyeRenderDescOut);

  byte ovrHmd_ConfigureRendering(OvrLibrary.ovrHmd hmd, RenderAPIConfig apiConfig, int hmdCaps, int distortionCaps,
      EyeDesc eyeDescIn[], EyeRenderDesc eyeRenderDescOut[]);

  byte ovrHmd_ConfigureRendering(Pointer hmd, RenderAPIConfig apiConfig, int hmdCaps, int distortionCaps,
      EyeDesc eyeDescIn[], EyeRenderDesc eyeRenderDescOut[]);

  @Deprecated
  com.oculusvr.capi.FrameTiming.ByValue ovrHmd_BeginFrame(Pointer hmd, int frameIndex);

  com.oculusvr.capi.FrameTiming.ByValue ovrHmd_BeginFrame(OvrLibrary.ovrHmd hmd, int frameIndex);

  @Deprecated
  void ovrHmd_EndFrame(Pointer hmd);

  void ovrHmd_EndFrame(OvrLibrary.ovrHmd hmd);

  @Deprecated
  com.oculusvr.capi.Posef.ByValue ovrHmd_BeginEyeRender(Pointer hmd, int eye);

  com.oculusvr.capi.Posef.ByValue ovrHmd_BeginEyeRender(OvrLibrary.ovrHmd hmd, int eye);

  @Deprecated
  void ovrHmd_EndEyeRender(Pointer hmd, int eye, com.oculusvr.capi.Posef.ByValue renderPose, Texture eyeTexture);

  void ovrHmd_EndEyeRender(OvrLibrary.ovrHmd hmd, int eye, com.oculusvr.capi.Posef.ByValue renderPose,
      Texture eyeTexture);

  @Deprecated
  EyeRenderDesc.ByValue ovrHmd_GetRenderDesc(Pointer hmd, EyeDesc.ByValue eyeDesc);

  EyeRenderDesc.ByValue ovrHmd_GetRenderDesc(OvrLibrary.ovrHmd hmd, EyeDesc.ByValue eyeDesc);

  @Deprecated
  byte ovrHmd_CreateDistortionMesh(Pointer hmd, EyeDesc.ByValue eyeDesc, int distortionCaps, Pointer uvScaleOffsetOut,
      DistortionMesh meshData);

  byte ovrHmd_CreateDistortionMesh(OvrLibrary.ovrHmd hmd, EyeDesc.ByValue eyeDesc, int distortionCaps,
      Vector2f uvScaleOffsetOut[], DistortionMesh meshData);

  byte ovrHmd_CreateDistortionMesh(Pointer hmd, EyeDesc.ByValue eyeDesc, int distortionCaps,
      Vector2f uvScaleOffsetOut[], DistortionMesh meshData);

  void ovrHmd_DestroyDistortionMesh(DistortionMesh meshData);

  @Deprecated
  void ovrHmd_GetRenderScaleAndOffset(Pointer hmd, EyeDesc.ByValue eyeDesc, int distortionCaps, Pointer uvScaleOffsetOut);

  void ovrHmd_GetRenderScaleAndOffset(OvrLibrary.ovrHmd hmd, EyeDesc.ByValue eyeDesc, int distortionCaps,
      Vector2f uvScaleOffsetOut[]);

  void ovrHmd_GetRenderScaleAndOffset(Pointer hmd, EyeDesc.ByValue eyeDesc, int distortionCaps,
      Vector2f uvScaleOffsetOut[]);

  @Deprecated
  com.oculusvr.capi.FrameTiming.ByValue ovrHmd_GetFrameTiming(Pointer hmd, int frameIndex);

  com.oculusvr.capi.FrameTiming.ByValue ovrHmd_GetFrameTiming(OvrLibrary.ovrHmd hmd, int frameIndex);

  @Deprecated
  com.oculusvr.capi.FrameTiming.ByValue ovrHmd_BeginFrameTiming(Pointer hmd, int frameIndex);

  com.oculusvr.capi.FrameTiming.ByValue ovrHmd_BeginFrameTiming(OvrLibrary.ovrHmd hmd, int frameIndex);

  @Deprecated
  void ovrHmd_EndFrameTiming(Pointer hmd);

  void ovrHmd_EndFrameTiming(OvrLibrary.ovrHmd hmd);

  @Deprecated
  void ovrHmd_ResetFrameTiming(Pointer hmd, int frameIndex, byte vsync);

  void ovrHmd_ResetFrameTiming(OvrLibrary.ovrHmd hmd, int frameIndex, byte vsync);

  @Deprecated
  com.oculusvr.capi.Posef.ByValue ovrHmd_GetEyePose(Pointer hmd, int eye);

  com.oculusvr.capi.Posef.ByValue ovrHmd_GetEyePose(OvrLibrary.ovrHmd hmd, int eye);

  @Deprecated
  void ovrHmd_GetEyeTimewarpMatrices(Pointer hmd, int eye, com.oculusvr.capi.Posef.ByValue renderPose, Pointer twmOut);

  void ovrHmd_GetEyeTimewarpMatrices(OvrLibrary.ovrHmd hmd, int eye, com.oculusvr.capi.Posef.ByValue renderPose,
      Matrix4f twmOut[]);

  void ovrHmd_GetEyeTimewarpMatrices(Pointer hmd, int eye, com.oculusvr.capi.Posef.ByValue renderPose,
      Matrix4f twmOut[]);

  Matrix4f.ByValue ovrMatrix4f_Projection(com.oculusvr.capi.FovPort.ByValue fov, float znear, float zfar,
      byte rightHanded);

  Matrix4f.ByValue ovrMatrix4f_OrthoSubProjection(Matrix4f.ByValue projection, Vector2f.ByValue orthoScale,
      float orthoDistance, float eyeViewAdjustX);

  double ovr_GetTimeInSeconds();

  double ovr_WaitTillTime(double absTime);

  @Deprecated
  byte ovrHmd_ProcessLatencyTest(Pointer hmd, Pointer rgbColorOut);

  byte ovrHmd_ProcessLatencyTest(OvrLibrary.ovrHmd hmd, ByteBuffer rgbColorOut);

  @Deprecated
  String ovrHmd_GetLatencyTestResult(Pointer hmd);

  String ovrHmd_GetLatencyTestResult(OvrLibrary.ovrHmd hmd);

  @Deprecated
  double ovrHmd_GetMeasuredLatencyTest2(Pointer hmd);

  double ovrHmd_GetMeasuredLatencyTest2(OvrLibrary.ovrHmd hmd);

  @Deprecated
  float ovrHmd_GetFloat(Pointer hmd, Pointer propertyName, float defaultVal);

  float ovrHmd_GetFloat(OvrLibrary.ovrHmd hmd, String propertyName, float defaultVal);

  @Deprecated
  byte ovrHmd_SetFloat(Pointer hmd, Pointer propertyName, float value);

  byte ovrHmd_SetFloat(OvrLibrary.ovrHmd hmd, String propertyName, float value);

  @Deprecated
  int ovrHmd_GetFloatArray(Pointer hmd, Pointer propertyName, FloatByReference values, int arraySize);

  int ovrHmd_GetFloatArray(OvrLibrary.ovrHmd hmd, String propertyName, FloatBuffer values, int arraySize);

  @Deprecated
  byte ovrHmd_SetFloatArray(Pointer hmd, Pointer propertyName, FloatByReference values, int arraySize);

  byte ovrHmd_SetFloatArray(OvrLibrary.ovrHmd hmd, String propertyName, FloatBuffer values, int arraySize);

  @Deprecated
  String ovrHmd_GetString(Pointer hmd, Pointer propertyName, Pointer defaultVal);

  String ovrHmd_GetString(OvrLibrary.ovrHmd hmd, String propertyName, String defaultVal);

  @Deprecated
  int ovrHmd_GetArraySize(Pointer hmd, Pointer propertyName);

  int ovrHmd_GetArraySize(OvrLibrary.ovrHmd hmd, String propertyName);

  /** Pointer to unknown (opaque) type */
  public static class HGLRC extends PointerType {
    public HGLRC(Pointer address) {
      super(address);
    }

    public HGLRC() {
      super();
    }
  };

  /** Pointer to unknown (opaque) type */
  public static class HDC extends PointerType {
    public HDC(Pointer address) {
      super(address);
    }

    public HDC() {
      super();
    }
  };

  /** Pointer to unknown (opaque) type */
  public static class HWND extends PointerType {
    public HWND(Pointer address) {
      super(address);
    }

    public HWND() {
      super();
    }
  };

  /** Pointer to unknown (opaque) type */
  public static class ovrHmd extends PointerType {
    public ovrHmd(Pointer address) {
      super(address);
    }

    public ovrHmd() {
      super();
    }

    public static OvrLibrary.ovrHmd create(int index) {
      return INSTANCE.ovrHmd_Create(index);
    }

    public void destroy() {
      INSTANCE.ovrHmd_Destroy(this);
    }

    public static OvrLibrary.ovrHmd createDebug(int type) {
      return INSTANCE.ovrHmd_CreateDebug(type);
    }

    public String getLastError() {
      return INSTANCE.ovrHmd_GetLastError(this);
    }

    public byte startSensor(int supportedCaps, int requiredCaps) {
      return INSTANCE.ovrHmd_StartSensor(this, supportedCaps, requiredCaps);
    }

    public void stopSensor() {
      INSTANCE.ovrHmd_StopSensor(this);
    }

    public void resetSensor() {
      INSTANCE.ovrHmd_ResetSensor(this);
    }

    public com.oculusvr.capi.SensorState.ByValue getSensorState(double absTime) {
      return INSTANCE.ovrHmd_GetSensorState(this, absTime);
    }

    public byte getSensorDesc(SensorDesc descOut) {
      return INSTANCE.ovrHmd_GetSensorDesc(this, descOut);
    }

    public HmdDesc getDesc() {
      HmdDesc desc = new HmdDesc();
      INSTANCE.ovrHmd_GetDesc(this, desc);
      return desc;
    }

    public com.oculusvr.capi.Sizei.ByValue getFovTextureSize(int eye, com.oculusvr.capi.FovPort.ByValue fov,
        float pixelsPerDisplayPixel) {
      return INSTANCE.ovrHmd_GetFovTextureSize(this, eye, fov, pixelsPerDisplayPixel);
    }

    public byte configureRendering(RenderAPIConfig apiConfig, int hmdCaps, int distortionCaps, EyeDesc eyeDescIn[],
        EyeRenderDesc eyeRenderDescOut[]) {
      return INSTANCE.ovrHmd_ConfigureRendering(this, apiConfig, hmdCaps, distortionCaps, eyeDescIn, eyeRenderDescOut);
    }

    public com.oculusvr.capi.FrameTiming.ByValue beginFrame(int frameIndex) {
      return INSTANCE.ovrHmd_BeginFrame(this, frameIndex);
    }

    public void endFrame() {
      INSTANCE.ovrHmd_EndFrame(this);
    }

    public com.oculusvr.capi.Posef.ByValue beginEyeRender(int eye) {
      return INSTANCE.ovrHmd_BeginEyeRender(this, eye);
    }

    public void endEyeRender(int eye, com.oculusvr.capi.Posef.ByValue renderPose, Texture eyeTexture) {
      INSTANCE.ovrHmd_EndEyeRender(this, eye, renderPose, eyeTexture);
    }

    public EyeRenderDesc.ByValue getRenderDesc(EyeDesc.ByValue eyeDesc) {
      return INSTANCE.ovrHmd_GetRenderDesc(this, eyeDesc);
    }

    public byte createDistortionMesh(EyeDesc.ByValue eyeDesc, int distortionCaps, Vector2f uvScaleOffsetOut[],
        DistortionMesh meshData) {
      return INSTANCE.ovrHmd_CreateDistortionMesh(this, eyeDesc, distortionCaps, uvScaleOffsetOut, meshData);
    }

    public void getRenderScaleAndOffset(EyeDesc.ByValue eyeDesc, int distortionCaps, Vector2f uvScaleOffsetOut[]) {
      INSTANCE.ovrHmd_GetRenderScaleAndOffset(this, eyeDesc, distortionCaps, uvScaleOffsetOut);
    }

    public com.oculusvr.capi.FrameTiming.ByValue getFrameTiming(int frameIndex) {
      return INSTANCE.ovrHmd_GetFrameTiming(this, frameIndex);
    }

    public com.oculusvr.capi.FrameTiming.ByValue beginFrameTiming(int frameIndex) {
      return INSTANCE.ovrHmd_BeginFrameTiming(this, frameIndex);
    }

    public void endFrameTiming() {
      INSTANCE.ovrHmd_EndFrameTiming(this);
    }

    public void resetFrameTiming(int frameIndex, byte vsync) {
      INSTANCE.ovrHmd_ResetFrameTiming(this, frameIndex, vsync);
    }

    public com.oculusvr.capi.Posef.ByValue getEyePose(int eye) {
      return INSTANCE.ovrHmd_GetEyePose(this, eye);
    }

    public void getEyeTimewarpMatrices(int eye, com.oculusvr.capi.Posef.ByValue renderPose, Matrix4f twmOut[]) {
      INSTANCE.ovrHmd_GetEyeTimewarpMatrices(this, eye, renderPose, twmOut);
    }

    public byte processLatencyTest(ByteBuffer rgbColorOut) {
      return INSTANCE.ovrHmd_ProcessLatencyTest(this, rgbColorOut);
    }

    public String getLatencyTestResult() {
      return INSTANCE.ovrHmd_GetLatencyTestResult(this);
    }

    public double getMeasuredLatencyTest2() {
      return INSTANCE.ovrHmd_GetMeasuredLatencyTest2(this);
    }

    public float getFloat(String propertyName, float defaultVal) {
      return INSTANCE.ovrHmd_GetFloat(this, propertyName, defaultVal);
    }

    public byte setFloat(String propertyName, float value) {
      return INSTANCE.ovrHmd_SetFloat(this, propertyName, value);
    }

    public int getFloatArray(String propertyName, FloatBuffer values, int arraySize) {
      return INSTANCE.ovrHmd_GetFloatArray(this, propertyName, values, arraySize);
    }

    public byte setFloatArray(String propertyName, FloatBuffer values, int arraySize) {
      return INSTANCE.ovrHmd_SetFloatArray(this, propertyName, values, arraySize);
    }

    public String getString(String propertyName, String defaultVal) {
      return INSTANCE.ovrHmd_GetString(this, propertyName, defaultVal);
    }

    public int getArraySize(String propertyName) {
      return INSTANCE.ovrHmd_GetArraySize(this, propertyName);
    }
  };

}
