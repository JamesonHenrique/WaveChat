package com.jhcs.wavechat.application.service;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.io.File.separator;
import static java.lang.System.currentTimeMillis;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {

    @Value("${application.file.uploads.media-output-path}")
    private String fileUploadPath;

    /**
     * Salva um arquivo no caminho especificado para o usuário.
     *
     * @param sourceFile O arquivo a ser salvo.
     * @param userId O ID do usuário.
     * @return O caminho do arquivo salvo.
     */
    public String saveFile(
            @Nonnull MultipartFile sourceFile,
            @Nonnull String userId
                          ) {
        final String fileUploadSubPath = "users" + separator + userId;
        return uploadFile(sourceFile, fileUploadSubPath);
    }

    /**
     * Faz o upload de um arquivo para o caminho especificado.
     *
     * @param sourceFile O arquivo a ser enviado.
     * @param fileUploadSubPath O subcaminho para o upload do arquivo.
     * @return O caminho do arquivo enviado.
     */
    private String uploadFile(
            @Nonnull MultipartFile sourceFile,
            @Nonnull String fileUploadSubPath
                             ) {
        final String finalUploadPath = fileUploadPath + separator + fileUploadSubPath;
        File targetFolder = new File(finalUploadPath);

        if (!targetFolder.exists()) {
            boolean folderCreated = targetFolder.mkdirs();
            if (!folderCreated) {
                log.warn("Falha ao criar a pasta de destino: " + targetFolder);
                return null;
            }
        }
        final String fileExtension = getFileExtension(sourceFile.getOriginalFilename());
        String targetFilePath = finalUploadPath + separator + currentTimeMillis() + "." + fileExtension;
        Path targetPath = Paths.get(targetFilePath);
        try {
            Files.write(targetPath, sourceFile.getBytes());
            log.info("Arquivo salvo em: " + targetFilePath);
            return targetFilePath;
        } catch (IOException e) {
            log.error("O arquivo não foi salvo", e);
        }
        return null;
    }

    /**
     * Obtém a extensão do arquivo a partir do nome do arquivo.
     *
     * @param fileName O nome do arquivo.
     * @return A extensão do arquivo.
     */
    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return "";
        }
        return fileName.substring(lastDotIndex + 1).toLowerCase();
    }
}