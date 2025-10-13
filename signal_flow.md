# SignalMain.scala Flowchart

This document describes the flow of the `SignalMain.scala` script, which simulates the Signal Protocol's key update logic.

```mermaid
graph TD
    A[main: 開始] --> B(初期状態の `SignalSession` を作成);
    B --> C{"sendMessage(\"メッセージ1\") を呼び出す"};

    subgraph "sendMessage の内部処理"
        C_IN[メッセージ受信] --> D(1. 新しい一時的なDH鍵ペアを生成);
        D --> E(2. DH鍵合意: 新しい秘密鍵と相手の公開鍵から<br/>`sharedSecret` (共通秘密)を計算);
        E --> F(3. ルート鍵の更新 (KDF¹):<br/>古い `rootKey` と `sharedSecret` から<br/>新しい `rootKey` と `receivingChainKey` を導出);
        F --> G(4. 送信チェーン鍵の更新 (KDF¹):<br/>古い `sendingChainKey` から<br/>新しい `sendingChainKey` を導出);
        G --> H(5. メッセージの暗号化:<br/><u>更新前</u>の `sendingChainKey` を使ってメッセージを暗号化);
        H --> I(6. 新しいセッション状態を返す:<br/>新しい `rootKey` と `sendingChainKey` を持つ<br/><u>新しい</u> `SignalSession` オブジェクトと暗号化結果を返す);
    end

    C --> I;
    I --> J(返された新しい `SignalSession` で状態を更新);
    J --> K{"sendMessage(\"メッセージ2\") を呼び出す"};
    K --> L(以下、メッセージごとに同じ処理を繰り返す...);
    L --> M[main: 終了];

    style H fill:#f9f,stroke:#333,stroke-width:2px
    style F fill:#ccf,stroke:#333,stroke-width:2px
    style G fill:#ccf,stroke:#333,stroke-width:2px
    
    footnote "¹ KDF (Key Derivation Function): 鍵導出関数。このコードでは `hkdf` 関数がシミュレートしています。";
```

```