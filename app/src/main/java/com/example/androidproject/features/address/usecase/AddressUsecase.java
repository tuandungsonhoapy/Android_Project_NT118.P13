package com.example.androidproject.features.address.usecase;

import android.util.Log;

import com.example.androidproject.core.errors.Failure;
import com.example.androidproject.core.utils.Either;
import com.example.androidproject.features.address.data.model.AddressModel;
import com.example.androidproject.features.address.data.repository.AddressRepositoryImpl;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AddressUsecase {
    private AddressRepositoryImpl addressRepository = new AddressRepositoryImpl(FirebaseFirestore.getInstance());

    public CompletableFuture<Either<Failure, List<AddressModel>>> getAddresses() {
        return addressRepository.getAddressRepository()
                .thenApply(r -> {
                    if (r.isRight()) {
                        return Either.right(r.getRight());
                    } else {
                        return Either.left(r.getLeft());
                    }
                });
    }

    public CompletableFuture<Either<Failure, List<AddressModel>>> getAddresses(String uid) {
        return addressRepository.getAddressRepository(uid)
                .thenApply(r -> {
                    if (r.isRight()) {
                        return Either.right(r.getRight());
                    } else {
                        return Either.left(r.getLeft());
                    }
                });
    }

    public CompletableFuture<Either<Failure, String>> addAddress(AddressModel address, long quantity) {
        return addressRepository.addAddressRepository(address, quantity).thenApply(r -> {
            if (r.isRight()) {
                return Either.right(r.getRight());
            } else {
                return Either.left(r.getLeft());
            }
        });
    }

    public CompletableFuture<Either<Failure, String>> editAddress(String id, AddressModel addressModel) {
        return addressRepository.updateAddressRepository(addressModel).thenApply(r -> {
            if (r.isRight()) {
                return Either.right(r.getRight());
            } else {
                return Either.left(r.getLeft());
            }
        });
    }

    public CompletableFuture<Either<Failure, String>> deleteAddress(String id) {
        return addressRepository.deleteAddressRepository(id).thenApply(r -> {
            if (r.isRight()) {
                return Either.right(r.getRight());
            } else {
                return Either.left(r.getLeft());
            }
        });
    }

    public CompletableFuture<Either<Failure, AddressModel>> getAddressById(String id) {
        return addressRepository.getAddressById(id).thenApply(r -> {
            if (r.isRight()) {
                return Either.right(r.getRight());
            } else {
                return Either.left(r.getLeft());
            }
        });
    }

    public CompletableFuture<Either<Failure, String>> updateAddressDefault(String id) {
        return addressRepository.updateAddressDefault(id).thenApply(r -> {
            if (r.isRight()) {
                return Either.right(r.getRight());
            } else {
                return Either.left(r.getLeft());
            }
        });
    }

    // sync users collection
    public CompletableFuture<Either<Failure, String>> updateAddressDefault(String addressId, String fullAddress) {
        return addressRepository.updateAddressDefault(addressId)
                .thenCompose(result -> {
                    if (result.isRight()) {
                        return addressRepository.updateUserAddress(addressId, fullAddress)
                                .thenApply(userUpdateResult -> {
                                    if (userUpdateResult.isRight()) {
                                        return Either.right("Địa chỉ mặc định đã được cập nhật thành công");
                                    } else {
                                        return Either.left(userUpdateResult.getLeft());
                                    }
                                });
                    } else {
                        return CompletableFuture.completedFuture(Either.left(result.getLeft()));
                    }
                });
    }

    public CompletableFuture<Either<Failure, String>> updateAddressDefault(String userId, String addressId, String fullAddress) {
        return addressRepository.updateAddressDefault(userId, addressId)
                .thenCompose(result -> {
                    if (result.isRight()) {
                        return addressRepository.updateUserAddress(userId, addressId, fullAddress)
                                .thenApply(userUpdateResult -> {
                                    if (userUpdateResult.isRight()) {
                                        return Either.right("Địa chỉ mặc định đã được cập nhật thành công");
                                    } else {
                                        return Either.left(userUpdateResult.getLeft());
                                    }
                                });
                    } else {
                        return CompletableFuture.completedFuture(Either.left(result.getLeft()));
                    }
                });
    }

    public CompletableFuture<Either<Failure, AddressModel>> getDefaultAddress() {
        return addressRepository.getDefaultAddress().thenApply(r -> {
            if (r.isRight()) {
                return Either.right(r.getRight());
            } else {
                return Either.left(r.getLeft());
            }
        });
    }
}
