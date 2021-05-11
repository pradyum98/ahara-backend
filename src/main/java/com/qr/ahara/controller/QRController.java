package com.qr.ahara.controller;

import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.qr.ahara.model.QRDataModel;
import com.qr.ahara.services.QRGeneratorService;

@RestController
public class QRController {
	private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/QRCode.png";

	@Autowired
	QRGeneratorService QRGeneratorService;

	@PostMapping(value = "/genrateAndDownloadQRCode/{width}/{height}")
	public void download(
			@PathVariable("width") Integer width,
			@PathVariable("height") Integer height , @RequestBody HashMap<String,Object> request)
					throws Exception {		
		QRDataModel codeText = new QRDataModel();
		codeText.setActive(true);
		codeText.setCreatedAt(new Date().getTime());
		codeText.setValidUpto(new Date().getTime() + 3600*1000);
		codeText.setCouponValue(Integer.valueOf(request.get("couponValue").toString()));
		
		QRGeneratorService.generateQRCodeImage(codeText.toString(), width, height, QR_CODE_IMAGE_PATH);
	}

	@GetMapping(value = "/genrateQRCode/{codeText}/{width}/{height}")
	public ResponseEntity<byte[]> generateQRCode(
			@PathVariable("codeText") String codeText,
			@PathVariable("width") Integer width,
			@PathVariable("height") Integer height)
					throws Exception {
		return ResponseEntity.status(HttpStatus.OK).body(QRGeneratorService.getQRCodeImage(codeText, width, height));
	}
}

