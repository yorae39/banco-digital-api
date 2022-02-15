package com.example.bancodigital.dto

import com.fasterxml.jackson.databind.annotation.JacksonStdImpl
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Getter
@Builder
@JacksonStdImpl
@NoArgsConstructor
@AllArgsConstructor
class QrCodeGenerationDTO(

    @NotBlank(message = "title must not be empty")
    @Size(max = 50, message = "Title should not exceed more than 50 characters")
    val title: String = "",

    @NotBlank(message = "message must not be empty")
    @Size(max = 5000, message = "message should not exceed more than 5000 characters")
    val message: String = "",

    @NotBlank(message = "generated-by-name must not be empty")
    val generatedByName: String = "",

    @NotBlank(message = "generated-for-name must not be empty")
    val generatedForName: String = "",
)