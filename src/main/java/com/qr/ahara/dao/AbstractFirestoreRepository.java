package com.qr.ahara.dao;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.qr.ahara.model.DocumentId;

//@Slf4j
public abstract class AbstractFirestoreRepository<T> {
	private final CollectionReference collectionReference;
	private final String collectionName;
	private final Class<T> parameterizedType;

	protected AbstractFirestoreRepository(Firestore firestore, String collection) {
		this.collectionReference = firestore.collection(collection);
		this.collectionName = collection;
		this.parameterizedType = getParameterizedType();
	}
	private Class<T> getParameterizedType(){
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		return (Class<T>)type.getActualTypeArguments()[0];
	}

	public boolean save(T model){
		String documentId = getDocumentId(model);
		ApiFuture<WriteResult> resultApiFuture = collectionReference.document(documentId).set(model);

		try {
//			log.info("{}-{} saved at{}", collectionName, documentId, resultApiFuture.get().getUpdateTime());
			System.out.println("{}-{} saved at{}"+ " "+ collectionName + " "+ documentId + " "+ resultApiFuture.get().getUpdateTime());
			return true;
		} catch (InterruptedException | ExecutionException e) {
//			log.error("Error saving {}={} {}", collectionName, documentId, e.getMessage());
			System.out.println("Error saving {}={} {}"+" "+ collectionName+ " "+ documentId+" "+ e.getMessage());
		}

		return false;

	}
	
	public boolean saveV2(HashMap<String,Object> model,String uniqueId){
		//String documentId = getDocumentIdV2(model);
		ApiFuture<WriteResult> resultApiFuture = collectionReference.document(uniqueId).set(model);

		try {
//			log.info("{}-{} saved at{}", collectionName, documentId, resultApiFuture.get().getUpdateTime());
			System.out.println("{}-{} saved at{}"+ " "+ collectionName + " "+ uniqueId + " "+ resultApiFuture.get().getUpdateTime());
			return true;
		} catch (InterruptedException | ExecutionException e) {
//			log.error("Error saving {}={} {}", collectionName, documentId, e.getMessage());
			System.out.println("Error saving {}={} {}"+" "+ collectionName+ " "+ uniqueId+" "+ e.getMessage());
		}

		return false;

	}

	public void delete(T model){
		String documentId = getDocumentId(model);
		ApiFuture<WriteResult> resultApiFuture = collectionReference.document(documentId).delete();

	}

	public List<T> retrieveAll(){
		ApiFuture<QuerySnapshot> querySnapshotApiFuture = collectionReference.get();

		try {
			List<QueryDocumentSnapshot> queryDocumentSnapshots = querySnapshotApiFuture.get().getDocuments();

			return queryDocumentSnapshots.stream()
					.map(queryDocumentSnapshot -> queryDocumentSnapshot.toObject(parameterizedType))
					.collect(Collectors.toList());

		} catch (InterruptedException | ExecutionException e) {
			System.out.println("Exception occurred while retrieving all document for {}"+" "+ collectionName);
//			log.error("Exception occurred while retrieving all document for {}", collectionName);
		}
		return Collections.<T>emptyList();

	}


	public Optional<T> get(String documentId){
		DocumentReference documentReference = collectionReference.document(documentId);
		ApiFuture<DocumentSnapshot> documentSnapshotApiFuture = documentReference.get();

		try {
			DocumentSnapshot documentSnapshot = documentSnapshotApiFuture.get();

			if(documentSnapshot.exists()){
				return Optional.ofNullable(documentSnapshot.toObject(parameterizedType));
			}

		} catch (InterruptedException | ExecutionException e) {
			System.out.println("Exception occurred retrieving: {} {}, {}"+" "+ collectionName+" "+ documentId+" "+ e.getMessage());
			//log.error("Exception occurred retrieving: {} {}, {}", collectionName, documentId, e.getMessage());
		}

		return Optional.empty();

	}


	protected String getDocumentId(T t) {
//		Object key;
//		Class clzz = t.getClass();
//		do{
//			key = getKeyFromFields(clzz, t);
//			clzz = clzz.getSuperclass();
//		} while(key == null && clzz != null);
//
//		if(key==null){
			return UUID.randomUUID().toString();
//		}
//		return String.valueOf(key);
	}
	
	protected String getDocumentIdV2(HashMap<String,Object> t) {

			return UUID.randomUUID().toString();

	}

	private Object getKeyFromFields(Class<?> clazz, Object t) {

		return Arrays.stream(clazz.getDeclaredFields())
				.filter(field -> field.isAnnotationPresent(DocumentId.class))
				.findFirst()
				.map(field -> getValue(t, field))
				.orElse(null);
	}

	@Nullable
	private Object getValue(Object t, java.lang.reflect.Field field) {
		field.setAccessible(true);
		try {
			return field.get(t);
		} catch (IllegalAccessException e) {
			System.out.println("Error in getting documentId key"+" "+ e);
//			log.error("Error in getting documentId key", e);
		}
		return null;
	}

	protected CollectionReference getCollectionReference(){
		return this.collectionReference;
	}
	protected Class<T> getType(){ return this.parameterizedType; }
}