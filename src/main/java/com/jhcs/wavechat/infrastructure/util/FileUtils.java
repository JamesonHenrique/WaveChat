package com.jhcs.wavechat.infrastructure.util;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class FileUtils {

    /**
     * Construtor privado para evitar instanciação.
     */
    private FileUtils() {}

    /**
     * Lê o conteúdo de um arquivo a partir de uma URL fornecida.
     *
     * @param fileUrl URL do arquivo a ser lido.
     * @return Um array de bytes contendo o conteúdo do arquivo, ou um array vazio se o arquivo não for encontrado ou se a URL estiver em branco.
     */
    public static byte[] readFileFromLocation(String fileUrl) {
        if (StringUtils.isBlank(fileUrl)) {
            return new byte[0];
        }
        try {
            Path filePath = new File(fileUrl).toPath();
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            log.warn("Arquivo não encontrado no caminho {}", fileUrl);
        }
        return new byte[0];
    }
}