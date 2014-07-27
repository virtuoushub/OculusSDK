project(OculusRoomTiny)

set(SOURCE_FILES
    RenderTiny_D3D11_Device.cpp
    RenderTiny_D3D11_Device.h
    OculusRoomTiny2.rc
    Win32_OculusRoomTiny.cpp
    Win32_OculusRoomTiny_Util.cpp
    OculusRoomModel.cpp
)

set(EXTRA_LIBS 
    OVR_C
)

if (WIN32)
    find_package( DirectX REQUIRED)
    list(APPEND EXTRA_LIBS ${DirectX_D3D10_LIBRARY})
    list(APPEND EXTRA_LIBS ${DirectX_D3DX10_LIBRARY})
    list(APPEND EXTRA_LIBS ${DirectX_D3D10_1_LIBRARY})
    list(APPEND EXTRA_LIBS ${DirectX_D3D11_LIBRARY})
    list(APPEND EXTRA_LIBS ${DirectX_D3DX11_LIBRARY})
    list(APPEND EXTRA_LIBS d3dcompiler DXGI dxguid)
endif()

if(WIN32)

    add_executable(${PROJECT_NAME} WIN32 ${SOURCE_FILES} )

elseif(APPLE)

    add_executable(${PROJECT_NAME} MACOSX_BUNDLE ${SOURCE_FILES} )

else()

    add_executable(${PROJECT_NAME} ${SOURCE_FILES} )

endif()

target_link_libraries(${PROJECT_NAME} ${EXTRA_LIBS})



