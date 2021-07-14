package com.qr.ahara.dao;

import org.springframework.stereotype.Repository;

import com.google.cloud.firestore.Firestore;
import com.qr.ahara.model.QRDataModel;

@Repository
public class QRDataRepository extends AbstractFirestoreRepository<QRDataModel> {
    protected QRDataRepository(Firestore firestore) {
        super(firestore, "qrData");
    }
}