package com.qr.ahara.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.qr.ahara.dao.QRDataRepository;
import com.qr.ahara.model.QRDataModel;
import com.qr.ahara.services.QRGeneratorService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@RestController
public class QRController {
	private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/QRCode.png";

	@Autowired
	QRGeneratorService QRGeneratorService;
	
	@Autowired
	private QRDataRepository qrDataRepository;
	
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
	
	@GetMapping(value = "/")
	public ResponseEntity<String> download()throws Exception {		
		return ResponseEntity.status(HttpStatus.OK).body("Success");
	}

	@PostMapping(value = "/genrateQRCode/{width}/{height}" )
	public ResponseEntity<ArrayList<byte[]>> generateQRCode(
			@PathVariable("width") Integer width,
			@PathVariable("height") Integer height  , @RequestBody HashMap<String,Object> request)
					throws Exception {
		ArrayList<QRDataModel> totalCouponsData = new ArrayList<QRDataModel>();
		ArrayList<byte[]> responseOutput = new ArrayList<byte[]>();
		int numberOfCoupons = Integer.valueOf(request.get("numberOfCoupons").toString());
		String uniqueId = UUID.randomUUID().toString();
		while(numberOfCoupons > 0) {
			QRDataModel codeText = new QRDataModel();
			codeText.setCouponId(UUID.randomUUID().toString());
			codeText.setActive(true);
			codeText.setGenerationId(uniqueId);
			codeText.setCreatedAt(new Date().getTime());
			codeText.setValidUpto(new Date().getTime() + 3600*1000);
			codeText.setCouponValue(Integer.valueOf(request.get("couponValue").toString()));
			codeText.setAadharNumber(request.get("aadhaarNumber").toString());
			codeText.setNameOfCouponHolder(request.get("nameOfCouponHolder").toString());
			codeText.setNameOfHospital(request.get("nameOfHospital").toString());
			codeText.setLocation(request.get("location").toString());
			codeText.setIpdRegistrationNumber(request.get("ipdRegistrationNumber").toString());
			byte[] qrImage = QRGeneratorService.getQRCodeImage(codeText.toString(), width, height);
			codeText.setQrCodeImage(qrImage.toString());
			responseOutput.add(qrImage);
			totalCouponsData.add(codeText);
			numberOfCoupons--;
		}
		HashMap<String,Object> result = new HashMap<String,Object>();
		result.put("totalCouponsData", totalCouponsData);
		result.put("nameOfCouponHolder", request.get("nameOfCouponHolder").toString());
		result.put("createdAt", new Date());
		result.put("updatedAt",new Date());
		qrDataRepository.saveV2(result,uniqueId); 

		return ResponseEntity.status(HttpStatus.OK).body(responseOutput);
	}
	
	@PostMapping(value = "/createOrder",produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
	public ResponseEntity<HashMap<String,Object>> createOrder(@RequestBody HashMap<String,Object> request){
		HashMap<String,Object> response = new HashMap<String,Object>();
		try {
			RazorpayClient razorpayClient = new RazorpayClient("rzp_test_frRF8qc2Zug2nv", "6ARbwzTuHowkNrkujYXO8qDw");
			JSONObject orderRequest = new JSONObject();
			orderRequest.put("amount", Integer.valueOf(request.get("amount").toString())*100);
			orderRequest.put("currency", "INR");
			orderRequest.put("receipt", "order_rcptid_11");
			orderRequest.put("payment_capture", 1);

			Order order = razorpayClient.Orders.create(orderRequest);
			System.out.println(order);
			response.put("id", order.get("id").toString());
			response.put("currency", order.get("currency").toString());
			response.put("amount", order.get("amount").toString());
//			response.put("order", order);
		} catch (RazorpayException e) {
			// Handle Exception
			System.out.println(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}

