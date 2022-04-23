package com.example.myapplication.checkin_guest.util;

import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;

/*card service를 위해선 AID가 필요하다. AID는 최대 16바이트로 구성된다.
  AID 등록은 ISO/IEX 7816-5사양의 정의를 따른다.*/

public class CardService extends HostApduService {
    private static final String TAG = "CardService";
    // AID for our loyalty card service.
    private static final String SAMPLE_LOYALTY_CARD_AID = "F0010203040506";
    // ISO-DEP command HEADER for selecting an AID.
    // Format: [Class | Instruction | Parameter 1 | Parameter 2]
    private static final String SELECT_APDU_HEADER = "00A40400";
    // "OK" status word sent in response to SELECT AID command (0x9000)
    private static final byte[] SELECT_OK_SW = HexStringToByteArray("9000");
    // "UNKNOWN" status word sent in response to invalid APDU command (0x0000)
    private static final byte[] UNKNOWN_CMD_SW = HexStringToByteArray("0000");
    private static final byte[] SELECT_APDU = BuildSelectApdu(SAMPLE_LOYALTY_CARD_AID);

    /**
     * NFC 카드에 대한 연결이 끊긴 경우 호출됩니다.
     * 연결 끊김의 원인(링크 손실 또는 다른 AID 선택)
     * 독서자)
     */
    @Override
    public void onDeactivated(int reason) {
        Log.d(TAG, "onDeactivated : " + reason);
    }

    /**
     * 이 메서드는 원격 장치에서 명령 APDU를 수신하면 호출된다.
     * 응답 APDU는 이 메서드에서 바이트 배열을 반환함으로써 직접 제공될 수 있다.
     * 응답 APDU는 사용자가 가능한 한 빨리 전송되어야 합니다.
     * 이 방법을 호출할 때 근거리 무선 통신 판독기에 장치를 고정한다.
     *
     * <p class="note">에서 동일한 AID에 대해 등록된 서비스가 여러개인 경우
     * 메타 데이터 항목, 사용자가 명시적으로 선택한 경우에만 호출된다.
     * 기본 탭으로 사용하거나 다음 탭에만 사용할 수 있다.
     *
     * <p class="note">이 메서드는 응용 프로그램의 기본 스레드에서 실행된다.
     * 만약 응답 APDU를 즉시 반환할 수 없는 경우 null을 반환한다.
     * #sendRResponseAppdu(byte[]) 메서드는 나중에 실행한다.
     *
     * @param commandApdu 원격 장치에서 받은 APDU
     * @param extras 추가 데이터를 포함하는 묶음이다. null일 수 있다.
     * @return APDU를 포함하는 바이트 배열을 반환하거나, 응답 APDU를 보낼 수 없는 경우 null을 반한한다.
     */
    // BEGIN_INCLUDE(processCommandApdu)

    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {
        Log.d(TAG, "processCommandApdu");
        String account = "91759999";
        byte[] accountBytes = account.getBytes();
        return ConcatArrays(accountBytes, SELECT_OK_SW); //전달 데이터와 ok 신호를 보냄

    }
    // END_INCLUDE(processCommandApdu)

    /**
     * SELECT AID명령을 위한 APDU를 빌드한다. 
     * ISO 7816-4를 참조
     * 
     * @param aid Application ID(AID)를 선택
     * @return APDU AID명령에 대한 APDU반환
     */
    public static byte[] BuildSelectApdu(String aid) {
        // Format: [CLASS | INSTRUCTION | PARAMETER 1 | PARAMETER 2 | LENGTH | DATA]
        return HexStringToByteArray(SELECT_APDU_HEADER + String.format("%02X",
                aid.length() / 2) + aid);
    }

    /**
     * 바이트 배열을 16진수 문자열로 변환하는 유틸리티 메서드이다.
     *
     * @param bytes 변환할 바이트수
     * @return 16진수 표현을 포함하는 반환 문자열이다.
     */
    public static String ByteArrayToHexString(byte[] bytes) {
        final char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        char[] hexChars = new char[bytes.length * 2]; // Each byte has two hex characters (nibbles)
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF; // Cast bytes[j] to int, treating as unsigned value
            hexChars[j * 2] = hexArray[v >>> 4]; // Select hex character from upper nibble
            hexChars[j * 2 + 1] = hexArray[v & 0x0F]; // Select hex character from lower nibble
        }
        return new String(hexChars);
    }

    /**
     * 16진수 문자열을 바이트 문자열로 변환하는 메서드
     *
     * 16잔수 이외의 문자를 포함하는 입력 문자열 동작 X
     *
     * @param s 변환할 16진수 문자가 포함된 문자열
     * @return 입력에서 생성된 바이트 배열
     * @throws IllegalArgumentException if input length is incorrect
     */
    public static byte[] HexStringToByteArray(String s) throws IllegalArgumentException {
        int len = s.length();
        if (len % 2 == 1) {
            throw new IllegalArgumentException("Hex string must have even number of characters");
        }
        byte[] data = new byte[len / 2]; // Allocate 1 byte per 2 hex characters
        for (int i = 0; i < len; i += 2) {
            // Convert each character into a integer (base-16), then bit-shift into place
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    /**
     * 두 바이트 배열을 연결하는 메서드
     * @param first First array
     * @param rest Any remaining arrays
     * @return Concatenated copy of input arrays
     */
    public static byte[] ConcatArrays(byte[] first, byte[]... rest) {
        int totalLength = first.length;
        for (byte[] array : rest) {
            totalLength += array.length;
        }
        byte[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (byte[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }
}