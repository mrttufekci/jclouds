/**
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jclouds.snia.cdmi.v1;

import java.util.concurrent.TimeUnit;

import org.jclouds.concurrent.Timeout;
import org.jclouds.rest.annotations.Delegate;
import org.jclouds.snia.cdmi.v1.features.ContainerClient;
import org.jclouds.snia.cdmi.v1.features.DataClient;
import org.jclouds.snia.cdmi.v1.features.DomainClient;

/**
 * Provides synchronous access to CDMI.
 * <p/>
 * 
 * @see CDMIAsyncClient
 * @see <a href="http://www.snia.org/cdmi">api doc</a>
 * @author Adrian Cole
 */
@Timeout(duration = 60, timeUnit = TimeUnit.SECONDS)
public interface CDMIClient {

   /**
    * Provides synchronous access to Domain Object Resource Operations.
    */
   @Delegate
   DomainClient getDomainClient();

   /**
    * Provides synchronous access to Container Object Resource Operations.
    */
   @Delegate
   ContainerClient getContainerClient();

   /**
    * Provides synchronous access to Data Object Resource Operations.
    */
   @Delegate
   DataClient getDataClient();
}
