package com.halenteck.render;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import java.io.File;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL30.*;

public final class OpenGLUtils {

    private OpenGLUtils() {
    }

    static int loadShader(int type, String shaderCode) {
        int shader = glCreateShader(type);
        glShaderSource(shader, shaderCode);
        glCompileShader(shader);
        return shader;
    }

    static int createAndLinkProgram(final int vertexShaderHandle, final int fragmentShaderHandle) {
        int programHandle = glCreateProgram();

        if (programHandle != 0) {
            glAttachShader(programHandle, vertexShaderHandle);
            glAttachShader(programHandle, fragmentShaderHandle);
            glLinkProgram(programHandle);
        }

        if (programHandle == 0) {
            throw new RuntimeException("Error creating program.");
        }

        return programHandle;
    }

    static int loadTexture(String filePath) {
        URL url = OpenGLUtils.class.getResource(filePath);
        if (url == null) {
            throw new RuntimeException("Resource not found: " + filePath);
        }
        File file = new File(url.getFile());
        if (!file.exists()) {
            throw new RuntimeException("File not found: " + filePath);
        }

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        ByteBuffer image = STBImage.stbi_load(file.getAbsolutePath(), width, height, channels, 4);
        if (image == null) {
            System.out.println(file.getAbsolutePath());
            throw new RuntimeException("Failed to load a texture file!"
                    + System.lineSeparator() + STBImage.stbi_failure_reason());
        }

        int textureHandle = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureHandle);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(), height.get(), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);

        STBImage.stbi_image_free(image);
        return textureHandle;

    }

}
