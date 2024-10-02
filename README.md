# TimeTable

## 概要
このアプリは、特定の2路線間の電車の乗換案内を表示するアプリです。

## 使用動画
https://www.youtube.com/watch?v=seONX23nshQ

## 機能
- 乗換案内の表示（現在時刻および5分後、10分後、30分後）
- 時刻表データのCRUD操作（未実装・機能追加予定） 

## 環境
- **Android Studio:** 4.x以降
- **最小APIレベル:** 31
- **言語:** Java

## インストール方法
1. Android Studioをインストール
    - [Android Studioダウンロードページ](https://developer.android.com/studio)
2. このリポジトリをクローンします:
    ```bash
    git clone https://github.com/AijiY/TimeTable.git
    ```
3. データベースファイルをDB Browser for SQLite等で作成し、以下の場所に保存します。
   フォルダパス：TimeTable\app\src\main\assets\databases
    ファイル名：time_table.db
4. Android Studioでプロジェクトを開きます。
5. エミュレータまたは接続したデバイスでアプリをビルドして実行します。

## 使用技術
- **UI:** Jetpack Compose / XMLレイアウト
- **データベース:** Room Database
- **依存関係管理:** Gradle
- **ライブラリ:** Material Components etc.

## 作者
- 名前: Aiji
- GitHub: [AijiY](https://github.com/AijiY)
