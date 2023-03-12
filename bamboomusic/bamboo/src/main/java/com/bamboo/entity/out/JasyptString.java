package com.bamboo.entity.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JasyptString {
    private String encryString;

    private String decryptString;
}
