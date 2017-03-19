package com.hsbc.unattendedtest.bin;

import com.hsbc.unattendedtest.bin.model.Bin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BinRepository extends MongoRepository<Bin, String> {
}
