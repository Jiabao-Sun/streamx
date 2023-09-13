/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.streampark.common

import scala.language.implicitConversions
import scala.util.Try

package object zio {

  /** Similar to python's pprint, format print any type of instance. */
  @inline def toPrettyString(value: Any): String = Try(value match {
    case v: String => v
    case v => pprint.apply(v, height = 2000, showFieldNames = true).render
  }).getOrElse(value.toString)

  implicit class PrettyStringOps(value: Any) {
    @inline def prettyStr: String = toPrettyString(value)
  }

  /** Automatically converts value to Some value. */
  implicit def liftValueAsSome[A](value: A): Option[A] = Some(value)

  implicit class OptionTraversableOps[A](iter: Traversable[Option[A]]) {
    def filterSome: Traversable[A] = iter.filter(_.isDefined).map(_.get)
  }

}
