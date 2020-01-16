/*
 * This file is part of text, licensed under the MIT License.
 *
 * Copyright (c) 2017-2020 KyoriPowered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.kyori.text.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A name map.
 *
 * @param <T> the type
 */
public final class NameMap<T> {
  private final Map<String, T> nameToValue;
  private final Map<T, String> valueToName;

  private NameMap(final Map<String, T> nameToValue, final Map<T, String> valueToName) {
    this.nameToValue = nameToValue;
    this.valueToName = valueToName;
  }

  /**
   * Creates a name map.
   *
   * @param constants the constants
   * @param namer the name provider
   * @param <T> the type
   * @return the name map
   */
  public static <T extends Enum<T>> @NonNull NameMap<T> create(final T @NonNull [] constants, final @NonNull Function<T, String> namer) {
    final int length = constants.length;
    final Map<String, T> nameToValue = new HashMap<>(length);
    final Map<T, String> valueToName = new HashMap<>(length);
    for(int i = 0; i < length; i++) {
      final T constant = constants[i];
      final String name = namer.apply(constant);
      nameToValue.put(name, constant);
      valueToName.put(constant, name);
    }
    return new NameMap<>(Collections.unmodifiableMap(nameToValue), Collections.unmodifiableMap(valueToName));
  }

  /**
   * Gets the name for a value.
   *
   * @param value the value
   * @return the name
   */
  public @NonNull String name(final @NonNull T value) {
    return this.valueToName.get(value);
  }

  /**
   * Gets a value by its name.
   *
   * @param name the name
   * @return the value
   */
  public @NonNull Optional<T> value(final @NonNull String name) {
    return Optional.ofNullable(this.nameToValue.get(name));
  }

  /**
   * Gets a value by its name.
   *
   * @param name the name
   * @return the value
   * @deprecated use {@link #value(String)}
   */
  @Deprecated
  public @NonNull Optional<T> get(final @NonNull String name) {
    return this.value(name);
  }
}
