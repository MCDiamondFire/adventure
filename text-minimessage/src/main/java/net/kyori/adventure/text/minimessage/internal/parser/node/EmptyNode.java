// CHECKSTYLE:OFF
package net.kyori.adventure.text.minimessage.internal.parser.node;

import org.jetbrains.annotations.NotNull;

public class EmptyNode extends ElementNode {

  public EmptyNode(@NotNull final String sourceMessage) {
    super(null, null, sourceMessage);
  }

  @Override
  public @NotNull StringBuilder buildToString(final @NotNull StringBuilder sb, final int indent) {
    final char[] in = this.ident(indent);
    sb.append(in).append("EmptyNode()\n");
    return sb;
  }
}
