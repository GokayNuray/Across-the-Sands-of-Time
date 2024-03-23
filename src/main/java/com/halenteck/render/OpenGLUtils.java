package com.halenteck.render;

import static org.lwjgl.opengl.GL20.*;

public final class OpenGLUtils {

    private OpenGLUtils() {
    }

    static int loadShader(int type, String shaderCode) {
        int shader = glCreateShader(type);
        glShaderSource(shader, shaderCode);
        glCompileShader(shader);
        return shader;
    }

    static int createAndLinkProgram(final int vertexShaderHandle, final int fragmentShaderHandle, final String[] attributes) {
        int programHandle = glCreateProgram();

        if (programHandle != 0) {
            glAttachShader(programHandle, vertexShaderHandle);
            glAttachShader(programHandle, fragmentShaderHandle);
            if (attributes != null) {
                final int size = attributes.length;
                for (int i = 0; i < size; i++) {
                    glBindAttribLocation(programHandle, i, attributes[i]);
                }
            }
            glLinkProgram(programHandle);
        }

        if (programHandle == 0) {
            throw new RuntimeException("Error creating program.");
        }

        return programHandle;
    }

}
