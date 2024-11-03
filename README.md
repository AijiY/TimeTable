# 乗換案内アプリ

## 作成背景（Background）
　自分がよく使用する特定の路線の時刻表を確認するために、乗換案内アプリを作成しました。

## 開発環境（Development Environment）
### 技術（Technologies）
![badge](https://img.shields.io/badge/Java-21.0.4-ED8B00?logo=openjdk&logoColor=white)

### ツール（Tools）
![badge](https://img.shields.io/badge/Android%20Studio-2024.1.1-3DDC84?logo=androidstudio&logoColor=white)
![badge](https://img.shields.io/badge/Room-2.6.1-3DDC84?logo=room&logoColor=white)
![badge](https://img.shields.io/badge/DB%20Browser%20for%20SQLite-3.13.0-A0A0A0?logoColor=white)
![badge](https://img.shields.io/badge/Gradle-8.9-02303A?logo=gradle&logoColor=white)
![badge](https://img.shields.io/badge/GitHub-%23181717?logo=github&logoColor=white)

## 機能（Function）
- 特定の路線の乗換案内の表示（現在時刻および5分後、10分後、30分後）<br>
![demo ‐ Clipchampで作成](https://github.com/user-attachments/assets/9d9154b5-1d4c-4830-8c6e-17dfb499bcde)

## 工夫した点 (Points to Note)
　一般的な乗換案内アプリに対して、機能を減らしたうえで必要な情報を取得するまでの時間を短縮しました。

## 今後の課題（Future Improvements）
- **テストコード実装** ：データ取得が正常に行われるかを確認するためのテストコードの実装
- **外部APIの使用** ：時刻表データや祝日情報の取得に外部APIを使用する
