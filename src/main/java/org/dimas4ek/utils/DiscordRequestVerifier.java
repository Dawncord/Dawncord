package org.dimas4ek.utils;

/*public class DiscordRequestVerifier {
    private boolean isValidSignature(WebSocket webSocket, String body) {
        String signature = exchange.getRequestHeaders().getFirst("X-Signature-Ed25519");
        String timestamp = exchange.getRequestHeaders().getFirst("X-Signature-Timestamp");
        
        Ed25519Signer verifier = new Ed25519Signer();
        verifier.init(false, new Ed25519PublicKeyParameters(Hex.decode(Constants.CLIENT_KEY), 0));
        
        byte[] data = (timestamp + body).getBytes(StandardCharsets.UTF_8);
        verifier.update(data, 0, data.length);
        
        return verifier.verifySignature(Hex.decode(signature));
    }
}*/
