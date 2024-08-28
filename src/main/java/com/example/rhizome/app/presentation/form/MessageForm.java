package com.example.rhizome.app.presentation.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 画面に表示するエラーメッセージを管理するクラスです。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageForm {
    private String message;
}
