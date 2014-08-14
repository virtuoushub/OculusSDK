project (OVR_C)

include_directories(Include Src)

# Make sure to set up the OVR include paths
# Cross platform access to shader functionality
include_directories(../../3rdParty/glext)
include_directories(${OculusVR_SOURCE_DIR}/Include)
include_directories(${OculusVR_SOURCE_DIR}/Src)
include_directories(${OculusVR_SOURCE_DIR}/Src/Kernel)
include_directories(${OculusVR_SOURCE_DIR}/Src/Util)

file(GLOB SOURCE_FILES Src/*.cpp Src/*.h Src/GL/*.cpp Src/GL/*.h Include/*.h)
if (WIN32)
file(GLOB WIN32_SOURCE_FILES Src/D3D1X/*.cpp Src/D3D1X/*.h)
list(APPEND SOURCE_FILES ${WIN32_SOURCE_FILES})
endif()
add_library(OVR_C SHARED ${SOURCE_FILES})

# All platforms require GLEW and OpenGL
find_package(OpenGL REQUIRED)
target_link_libraries(OVR_C OculusVR ${OVR_LIBRARIES} ${OPENGL_LIBRARIES})
