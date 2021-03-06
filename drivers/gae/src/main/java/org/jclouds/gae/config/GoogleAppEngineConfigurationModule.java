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
package org.jclouds.gae.config;

import org.jclouds.concurrent.MoreExecutors;
import org.jclouds.concurrent.SingleThreaded;
import org.jclouds.concurrent.config.ConfiguresExecutorService;
import org.jclouds.concurrent.config.ExecutorServiceModule;
import org.jclouds.gae.GaeHttpCommandExecutorService;
import org.jclouds.http.HttpCommandExecutorService;
import org.jclouds.http.TransformingHttpCommandExecutorService;
import org.jclouds.http.TransformingHttpCommandExecutorServiceImpl;
import org.jclouds.http.config.ConfiguresHttpCommandExecutorService;

import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;

/**
 * Configures {@link GaeHttpCommandExecutorService}.
 * 
 * @author Adrian Cole
 */
@ConfiguresHttpCommandExecutorService
@ConfiguresExecutorService
@SingleThreaded
public class GoogleAppEngineConfigurationModule extends AbstractModule {
   private final Module executorServiceModule;

   public GoogleAppEngineConfigurationModule() {
      this(new ExecutorServiceModule(MoreExecutors.sameThreadExecutor(), MoreExecutors.sameThreadExecutor()));
   }

   /**
    * Used when you are creating multiple contexts in the same app.
    * 
    * @param currentRequestExecutorService
    * @see CurrentRequestExecutorServiceModule#currentRequestExecutorService
    */
   public GoogleAppEngineConfigurationModule(Module executorServiceModule) {
      this.executorServiceModule = executorServiceModule;
   }

   /**
    * Used when you are creating multiple contexts in the same app.
    * 
    * @param memoizedCurrentRequestExecutorService
    * @see CurrentRequestExecutorServiceModule#memoizedCurrentRequestExecutorService
    */
   public GoogleAppEngineConfigurationModule(Supplier<ListeningExecutorService> memoizedCurrentRequestExecutorService) {
      this.executorServiceModule = new CurrentRequestExecutorServiceModule(memoizedCurrentRequestExecutorService);
   }

   @Override
   protected void configure() {
      install(executorServiceModule);
      bind(TransformingHttpCommandExecutorService.class).to(TransformingHttpCommandExecutorServiceImpl.class);
      bindHttpCommandExecutorService();
   }

   protected void bindHttpCommandExecutorService() {
      bind(HttpCommandExecutorService.class).to(GaeHttpCommandExecutorService.class);
   }

   @Provides
   protected URLFetchService provideURLFetchService() {
      return URLFetchServiceFactory.getURLFetchService();
   }
}
