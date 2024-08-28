function convertToKatakana(input) {
    // 漢字とローマ字を削除
    const valueWithoutKanjiAndRomaji = input.value.replace(/[\u4E00-\u9FFF\u0041-\u009F]/g, '');

    // ひらがなをカタカナに変換
    const katakanaValue = valueWithoutKanjiAndRomaji.replace(/[\u3041-\u3096]/g, function (match) {
        const chr = match.charCodeAt(0) + 0x60;
        return String.fromCharCode(chr);
    });

    input.value = katakanaValue;
}

function logoutSubmit() {
    const form = document.createElement('form');
    form.setAttribute('method', 'post');
    form.setAttribute('action', 'logout');

    // 動的送信用のHidden
    // 動的にFormを作成する場合は何かしら送信しないと、正しく動作しない
    const inputField = document.createElement("input");
    inputField.setAttribute("type", "hidden");
    inputField.setAttribute("value", "logout");

    form.appendChild(inputField);

    // フォームをDOMに追加
    document.body.appendChild(form);

    form.submit();
}
