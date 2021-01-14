/**
 * Copyright 2015 StreamSets Inc.
 *
 * Licensed under the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.stage.processor.sample;

import com.example.stage.lib.sample.Errors;

import com.streamsets.pipeline.api.ErrorCode;
import com.streamsets.pipeline.api.Record;
import com.streamsets.pipeline.api.StageException;
import com.streamsets.pipeline.api.base.SingleLaneRecordProcessor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.csvreader.CsvReader;

public abstract class SampleProcessor extends SingleLaneRecordProcessor {
  private static final Logger LOG = LoggerFactory.getLogger(SampleProcessor.class);
  private Record record;
  private CsvReader csvR;
  private String path;


  /**
   * Gives access to the UI configuration of the stage provided by the {@link SampleDProcessor} class.
   */
  public abstract String getConfig();//为何这样写
  public abstract String getSample1Csv();
//  public abstract Map<String, String> getMapAttribute();

  /** {@inheritDoc} */
  @Override
  protected List<ConfigIssue> init() {
    // Validate configuration values and open any required resources.
    List<ConfigIssue> issues = super.init();
    path = getConfig();

    if (getConfig().equals("invalidValue")) {
      issues.add(
              getContext().createConfigIssue(
                      Groups.SAMPLE.name(), "config", Errors.SAMPLE_00, "Here's what's wrong..."
              )
      );
    }

    // If issues is not empty, the UI will inform the user of each configuration issue in the list.
    return issues;
  }

  /** {@inheritDoc} */
  @Override
  public void destroy() {
    // Clean up any open resources.
    super.destroy();
  }

  /** {@inheritDoc} */
  @Override
  protected void process(Record record, SingleLaneBatchMaker batchMaker) throws StageException {
    // TODO: Implement your record processing here, then add to the output batch.
    //LOG.info("Processing a record payment_type1: {}", record.get("/payment_type").getValueAsString());
    // This example is a no-op
    String recordValue = record.get("").getValueAsString();
    try {
      csvR = new CsvReader(path, ',', Charset.forName("UTF-8"));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    String csvHeader = getSample1Csv();
    try {
      csvR.readHeaders();
      while (csvR.readRecord()) {

        // 读一整行
        //String csvRecord = csvR.getRawRecord();
        String csvValue = csvR.get(csvHeader);
        if (recordValue.equals(csvValue)) {
          batchMaker.addRecord(record);
          break;
        }
        else {

        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    csvR.close();
//    batchMaker.addRecord(record);
  }

}