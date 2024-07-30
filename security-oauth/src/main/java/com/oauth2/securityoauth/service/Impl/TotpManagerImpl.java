//package com.oauth2.securityoauth.service.Impl;
//
//import com.oauth2.securityoauth.service.TotpManager;
//import dev.samstevens.totp.code.CodeVerifier;
//import dev.samstevens.totp.code.HashingAlgorithm;
//import dev.samstevens.totp.exceptions.QrGenerationException;
//import dev.samstevens.totp.qr.QrData;
//import dev.samstevens.totp.qr.QrGenerator;
//import dev.samstevens.totp.secret.SecretGenerator;
//import dev.samstevens.totp.util.Utils;
//import org.springframework.stereotype.Service;
//
//@Service
//public class TotpManagerImpl implements TotpManager {
//
//    private final SecretGenerator secretGenerator;
//
//    private final QrGenerator qrGenerator;
//
//    private final CodeVerifier codeVerifier;
//
//    public TotpManagerImpl(SecretGenerator secretGenerator, QrGenerator qrGenerator, CodeVerifier codeVerifier) {
//        this.secretGenerator = secretGenerator;
//        this.qrGenerator = qrGenerator;
//        this.codeVerifier = codeVerifier;
//    }
//
//    @Override
//    public String generateSecretKey() {
//        return secretGenerator.generate(); //32 bytes secret key
//    }
//
//    @Override
//    public String getQRCode(String secret) throws QrGenerationException {
//        QrData  qrData = new QrData.Builder().label("2FA Server")
//                .issuer("phuongcm")
//                .secret(secret)
//                .digits(6)
//                .period(30)
//                .algorithm(HashingAlgorithm.SHA512).build();
//        return Utils.getDataUriForImage(qrGenerator.generate(qrData), qrGenerator.getImageMimeType());
//    }
//
//    @Override
//    public boolean verifyTotp(String code, String secret) {
//        return codeVerifier.isValidCode(secret, code);
//    }
//}
