function loadBootstrap() {
    return new Promise((resolve, reject) => {
        // bootstraps CDN CSS
        var linkElement = document.createElement("link");
        linkElement.rel = "stylesheet";
        linkElement.href = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css";
        linkElement.integrity = "sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN";
        linkElement.crossOrigin = "anonymous";
        document.head.appendChild(linkElement);

        // bootstrap CDN js
        var bootstrapScript = document.createElement("script");
        bootstrapScript.src = 'https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js';
        bootstrapScript.integrity = 'sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL';
        bootstrapScript.crossOrigin = 'anonymous';
        document.head.appendChild(bootstrapScript);

        bootstrapScript.onload = resolve;
        bootstrapScript.onerror = reject;

        // inconfy CDN js
        var iconifyScript = document.createElement("script");
        iconifyScript.src = "https://code.iconify.design/iconify-icon/1.0.7/iconify-icon.min.js";
        iconifyScript.integrity = "sha384-ZBvlAMcOinSpqbKp+h0PpJxrDWCO8veRjvEhIc+Wg2Um8ZUKrbyNtJChA7FhNtCF";
        iconifyScript.crossOrigin = "anonymous";
        document.head.appendChild(iconifyScript);

        iconifyScript.onload = resolve;
        iconifyScript.onerror = reject;
    });
}

// 使用 Promise 來確保 Bootstrap 載入完成
loadBootstrap().then(() => {
    // 在這裡執行需要 Bootstrap 功能的代碼
}).catch((error) => {
    console.error('無法載入 Bootstrap:', error);
});
