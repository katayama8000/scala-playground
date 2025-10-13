package signal

import scala.util.Random

object SignalMain {
  def main(args: Array[String]): Unit = {
    println(
      "This is a simplified simulation of the Signal Protocol's key update logic."
    )

    // 初期状態のセッションを作成
    val initialSession = SignalSession(
      rootKey = "InitialRootKey",
      sendingChainKey = "InitialSendingKey",
      remoteDhPublicKey = "InitialRemoteDHPubKey"
    )

    // メッセージを送信し、セッション状態を更新
    val (sessionAfterFirstMessage, result1) =
      initialSession.sendMessage("Hello, Alice!")
    println(
      s"送信結果1: ${result1.encryptedMessage}, 新しいDH公開鍵: ${result1.newDhPublicKey}"
    )

    val (sessionAfterSecondMessage, result2) =
      sessionAfterFirstMessage.sendMessage("How are you?")
    println(
      s"送信結果2: ${result2.encryptedMessage}, 新しいDH公開鍵: ${result2.newDhPublicKey}"
    )

    val (sessionAfterThirdMessage, result3) =
      sessionAfterSecondMessage.sendMessage("Goodbye!")
    println(
      s"送信結果3: ${result3.encryptedMessage}, 新しいDH公開鍵: ${result3.newDhPublicKey}"
    )
  }

  // 鍵ペアを表すケースクラス
  case class DhKeyPair(privateKey: String, publicKey: String)

  // 秘密の値から、新しい鍵を安全に導出する（HKDFのシミュレーション）
  def hkdf(key: String, salt: String, info: String): String = {
    // 実際のHKDFは複雑ですが、新しい文字列を返すことで「鍵の導出」をシミュレート
    s"NewKey($key, $salt, $info)"
  }

  // 自分の秘密鍵と相手の公開鍵から共通の秘密鍵を生成（DH合意のシミュレーション）
  def dhAgreed(myPrivateKey: String, remotePublicKey: String): String = {
    // 実際のDiffie-Hellman鍵交換をシミュレート
    s"SharedSecret($myPrivateKey, $remotePublicKey)"
  }

  // 仮の暗号化関数
  def encrypt(key: String, message: String): String = {
    s"Encrypted($message, Key:$key)"
  }

  // 仮のDH鍵生成
  def dhGenerate(): DhKeyPair = {
    val id = Random.alphanumeric.take(8).mkString
    DhKeyPair(
      privateKey = s"NewDHPriv_$id",
      publicKey = s"NewDHPuib_$id"
    )
  }

  // メッセージ送信の結果
  case class MessageResult(
      encryptedMessage: String,
      newDhPublicKey: String // 相手に送るための新しい公開鍵
  )

  // === Signalプロトコル - 鍵更新ロジック（イミュータブルな状態） ===

  // セッションの状態を表すケースクラス
  case class SignalSession(
      rootKey: String, // 現在のルート鍵
      sendingChainKey: String, // 送信チェーン鍵
      remoteDhPublicKey: String // 相手の現在の一時公開鍵
  ) {

    /** メッセージを暗号化し、鍵を更新した新しいSignalSessionを返す
      *
      * @param message
      *   送信するメッセージ
      * @returns
      *   (新しいSignalSessionの状態, 暗号化結果)
      */
    def sendMessage(message: String): (SignalSession, MessageResult) = {
      println(s"\n--- メッセージ送信: \"$message\" ---")
      println(s"[送信前] Current Root Key (RK): $rootKey")
      println(s"[送信前] Current Sending Key (CK_send): $sendingChainKey")

      // 1. 新しい「一時的なDH鍵ペア」を生成する (ダブルラチェットの歯)
      val newDhKeyPair = dhGenerate()
      println(s"生成した一時DH公開鍵: ${newDhKeyPair.publicKey}")

      // 2. ルートラチェット: 自分の新しい秘密鍵と、相手の古い公開鍵から新しいルート鍵を導出
      val sharedSecret = dhAgreed(newDhKeyPair.privateKey, remoteDhPublicKey)
      println(s"DH共有シークレット: $sharedSecret")

      // ルート鍵の更新: RKとSharedSecretから、新しいRKと新しい受信チェーン鍵を導出
      val newRootKey = hkdf(rootKey, sharedSecret, "RootKeyUpdate")
      val newReceivingChainKey =
        hkdf(rootKey, sharedSecret, "ReceivingChainKey")

      // 3. チェーンラチェット: メッセージ暗号化鍵の導出とメッセージの暗号化
      val currentEncryptionKey = sendingChainKey // 現在の送信鍵を使用
      val encryptedMessage = encrypt(currentEncryptionKey, message)
      println(s"暗号化に使用した鍵: $currentEncryptionKey")
      println(s"暗号化されたメッセージ: $encryptedMessage")

      // 4. 次の送信のためのチェーン鍵を更新
      val newSendingChainKey =
        hkdf(sendingChainKey, "Salt", "SendingChainUpdate")

      // 5. 新しいセッション状態を構築（イミュータブルな更新）
      val newSession = SignalSession(
        rootKey = newRootKey,
        sendingChainKey = newSendingChainKey,
        remoteDhPublicKey = newDhKeyPair.publicKey // 相手の公開鍵を自分の新しい鍵に更新
      )

      println(s"[送信後] New Root Key (RK): ${newSession.rootKey}")
      println(s"[送信後] New Sending Key (CK_send): ${newSession.sendingChainKey}")
      println(s"導出された次の受信鍵 (相手が使う): $newReceivingChainKey")

      val result = MessageResult(encryptedMessage, newDhKeyPair.publicKey)

      (newSession, result)
    }
  }
}
