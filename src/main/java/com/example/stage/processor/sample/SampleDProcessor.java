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

import _ss_com.streamsets.pipeline.lib.el.RecordEL;
import _ss_com.streamsets.pipeline.lib.el.VaultEL;
import com.streamsets.pipeline.api.ConfigDef;
import com.streamsets.pipeline.api.ConfigGroups;
import com.streamsets.pipeline.api.GenerateResourceBundle;
import com.streamsets.pipeline.api.StageDef;

import java.util.HashMap;
import java.util.Map;

@StageDef(
    version = 1,
    label = "Sample Processor",
    description = "",
    icon = "default.png",
    onlineHelpRefUrl = ""
)
@ConfigGroups(Groups.class)
@GenerateResourceBundle
public class SampleDProcessor extends SampleProcessor {

  @ConfigDef(
      required = true,
      type = ConfigDef.Type.STRING,
      defaultValue = "default",
      label = "Config Path",
      displayPosition = 10,
      group = "SAMPLE"
  )
  public String config;

  @ConfigDef(
          required = true,
          type = ConfigDef.Type.STRING,
          defaultValue = "default",
          label = "sample1Csv",
          displayPosition = 11,
          group = "SAMPLE"
  )
  public String sample1Csv;

  @ConfigDef(
          required = false,
          type = ConfigDef.Type.MAP,
          label = "Config Map",
          description = "Headers to include in the request",
          evaluation = ConfigDef.Evaluation.EXPLICIT,
          displayPosition = 12,
          displayMode = ConfigDef.DisplayMode.BASIC,
          elDefs = {RecordEL.class, VaultEL.class},
          group = "HTTP"
  )
  public Map<String, String> configMap = new HashMap<>();

  /** {@inheritDoc} */
  @Override
  public String getConfig() {
    return config;
  }

  @Override
  public String getSample1Csv() {
    return sample1Csv;
  }

  @Override
  public Map<String, String> getConfigMap() {
    return configMap;
  }

}