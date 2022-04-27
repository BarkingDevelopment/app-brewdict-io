
# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.18.1)

# Declares and names the project.

project("Brewdict.io")

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
             brewdict

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
            brewdict.cpp
            BrewdictPredictionModel.mat
            CompactEnsemble.c
            CompactEnsemble.h
            CompactRegressionTree.c
            CompactRegressionTree.h
            predict.c
            predict.h
            predict_data.c
            predict_data.h
            predict_initialize.c
            predict_initialize.h
            predict_internal_types.h
            predict_terminate.c
            predict_terminate.h
            predict_types.h
            predictOneWithCache.c
            predictOneWithCache.h
            rt_nonfinite.c
            rt_nonfinite.h
            rtGetInf.c
            rtGetInf.h
            rtGetNaN.c
            rtGetNaN.h
            rtwtypes.h
            tmwtypes.h
            updateCache.c
            updateCache.h )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
                        brewdict

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )
