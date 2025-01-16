package com.jhcs.wavechat.domain.common;

import lombok.*;

/**
 * Classe que representa uma resposta de string.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StringResponse {
    /**
     * A resposta em formato de string.
     */
    private String response;
}