package app.qrme.lib.data.service;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.ICredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Google2FAService implements ICredentialRepository {

    private GoogleAuthenticator gAuth;

    @Autowired
    public Google2FAService() {
        this.gAuth = new GoogleAuthenticator();
        this.gAuth.setCredentialRepository(this);
    }

    public String createKeyFor(String email) {
        return this.gAuth.createCredentials(email).getKey();
    }

    public boolean isCodeValid(String secret, Integer code) {
        return this.gAuth.authorize(secret, code);
    }

    @Override
    public String getSecretKey(String username) {
        return null;
    }

    @Override
    public void saveUserCredentials(String username, String secretKey, int validationCode, List<Integer> scratchCodes) {

    }
}
