package it.enuwa.sfdc.utils;

import com.eternitywall.ots.OpenTimestamps;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by festini on 5/13/17.
 */
public class BlockchainUtils {

    public static byte[] stamp(String encryptedPayload) throws IOException {
        byte[] blob = encryptedPayload.getBytes(StandardCharsets.UTF_8);
        return OpenTimestamps.stamp(blob);
    }
}
