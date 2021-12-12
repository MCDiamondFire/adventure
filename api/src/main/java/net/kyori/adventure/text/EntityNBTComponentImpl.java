/*
 * This file is part of adventure, licensed under the MIT License.
 *
 * Copyright (c) 2017-2021 KyoriPowered
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
package net.kyori.adventure.text;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import net.kyori.adventure.text.format.Style;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.util.Objects.requireNonNull;

final class EntityNBTComponentImpl extends NBTComponentImpl<EntityNBTComponent, EntityNBTComponent.Builder> implements EntityNBTComponent {
  private final String selector;

  EntityNBTComponentImpl(final @NotNull List<? extends ComponentLike> children, final @NotNull Style style, final String nbtPath, final boolean interpret, final @Nullable ComponentLike separator, final String selector) {
    super(children, style, nbtPath, interpret, separator);
    this.selector = selector;
  }

  @Override
  public @NotNull EntityNBTComponent nbtPath(final @NotNull String nbtPath) {
    if (Objects.equals(this.nbtPath, nbtPath)) return this;
    return new EntityNBTComponentImpl(this.children, this.style, requireNonNull(nbtPath, "nbt path"), this.interpret, this.separator, this.selector);
  }

  @Override
  public @NotNull EntityNBTComponent interpret(final boolean interpret) {
    if (this.interpret == interpret) return this;
    return new EntityNBTComponentImpl(this.children, this.style, this.nbtPath, interpret, this.separator, this.selector);
  }

  @Override
  public @Nullable Component separator() {
    return this.separator;
  }

  @Override
  public @NotNull EntityNBTComponent separator(final @Nullable ComponentLike separator) {
    return new EntityNBTComponentImpl(this.children, this.style, this.nbtPath, this.interpret, separator, this.selector);
  }

  @Override
  public @NotNull String selector() {
    return this.selector;
  }

  @Override
  public @NotNull EntityNBTComponent selector(final @NotNull String selector) {
    if (Objects.equals(this.selector, selector)) return this;
    return new EntityNBTComponentImpl(this.children, this.style, this.nbtPath, this.interpret, this.separator, requireNonNull(selector, "selector"));
  }

  @Override
  public @NotNull EntityNBTComponent children(final @NotNull List<? extends ComponentLike> children) {
    return new EntityNBTComponentImpl(requireNonNull(children, "children"), this.style, this.nbtPath, this.interpret, this.separator, this.selector);
  }

  @Override
  public @NotNull EntityNBTComponent style(final @NotNull Style style) {
    return new EntityNBTComponentImpl(this.children, requireNonNull(style, "style"), this.nbtPath, this.interpret, this.separator, this.selector);
  }

  @Override
  public boolean equals(final @Nullable Object other) {
    if (this == other) return true;
    if (!(other instanceof EntityNBTComponent)) return false;
    if (!super.equals(other)) return false;
    final EntityNBTComponentImpl that = (EntityNBTComponentImpl) other;
    return Objects.equals(this.selector, that.selector());
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = (31 * result) + this.selector.hashCode();
    return result;
  }

  @Override
  protected @NotNull Stream<? extends ExaminableProperty> examinablePropertiesWithoutChildren() {
    return Stream.concat(
      Stream.of(
        ExaminableProperty.of("selector", this.selector)
      ),
      super.examinablePropertiesWithoutChildren()
    );
  }

  @Override
  public EntityNBTComponent.@NotNull Builder toBuilder() {
    return new BuilderImpl(this);
  }

  static final class BuilderImpl extends NBTComponentImpl.BuilderImpl<EntityNBTComponent, EntityNBTComponent.Builder> implements EntityNBTComponent.Builder {
    private @Nullable String selector;

    BuilderImpl() {
    }

    BuilderImpl(final @NotNull EntityNBTComponent component) {
      super(component);
      this.selector = component.selector();
    }

    @Override
    public EntityNBTComponent.@NotNull Builder selector(final @NotNull String selector) {
      this.selector = requireNonNull(selector, "selector");
      return this;
    }

    @Override
    public @NotNull EntityNBTComponent build() {
      if (this.nbtPath == null) throw new IllegalStateException("nbt path must be set");
      if (this.selector == null) throw new IllegalStateException("selector must be set");
      return new EntityNBTComponentImpl(this.children, this.buildStyle(), this.nbtPath, this.interpret, this.separator, this.selector);
    }
  }
}
