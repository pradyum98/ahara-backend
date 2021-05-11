package com.qr.ahara.services;

import java.io.IOException;

import com.google.zxing.WriterException;

public interface QRGeneratorService {
	public void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException;
	
	public byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException;
}
