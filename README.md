# Eisner — Comic Reader

> *El lector de cómics que Will hubiera querido*

App Android nativa con soporte completo de CBZ, CBR, ZIP, RAR y PDF.

---

## Instalar la app (usuarios)

1. Ve a la pestaña **[Releases](../../releases)**
2. Descarga `eisner.apk`
3. Abre el APK en tu Android
4. Si aparece "fuentes desconocidas", acepta → Instalar
5. ¡Listo!

---

## Desarrollar localmente (requiere Node.js)

```bash
# 1. Clona el repo
git clone https://github.com/velismo/eisner.git
cd eisner

# 2. Instala dependencias
npm install

# 3. Añade plataforma Android (solo la primera vez)
npx cap add android

# 4. Sincroniza assets web
npx cap sync android

# 5. Abre en Android Studio (para emular o compilar)
npx cap open android
```

---

## Compilar APK manualmente

```bash
cd android
./gradlew assembleDebug
# APK en: android/app/build/outputs/apk/debug/app-debug.apk
```

---

## Formatos soportados

| Formato | Soporte |
|---------|---------|
| CBZ / ZIP | ✅ Nativo |
| CBR / RAR | ✅ Nativo (junrar) |
| PDF | ✅ Nativo |
| 7Z, TAR | 🔜 Próximamente |

---

## Roadmap

- [x] Lector base con navegación táctil
- [x] Guided View con detección de viñetas por visión computacional
- [ ] Detección de viñetas por IA (Claude Vision)
- [ ] Biblioteca de cómics con portadas
- [ ] Soporte manga (RTL)
- [ ] Marcadores y progreso de lectura
- [ ] iOS (requiere Apple Developer Account)
