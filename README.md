Copyright &#169; 2018 Konstantin Pelz<br>
<br>
This program is free software: you can redistribute it and/or modify it under
the terms of the GNU General Public License as published by the Free Software
Foundation, either version 3 of the License, or (at your option) any later
version. This program is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
details. You should have received a copy of the GNU General Public License
along with this program. If not, see
<a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>.<br>
<br>
Equivalence to <code>struct</code> in Python. This plugin converts C structs
into Java values and vica versa.<br>
<br>
<b>Byte Order, Size &amp; Alignment</b><br>
<table border="1px solid black" summary ="">
<tr>
<th scope="col">Character</th>
<th scope="col">Byte order</th>
<th scope="col">Size</th>
<th scope="col">Alignment</th>
</tr>
<tr>
<td><code>@</code></td>
<td>native</td>
<td>native</td>
<td>native</td>
</tr>
<tr>
<td><code>=</code></td>
<td>native</td>
<td>standard</td>
<td>none</td>
</tr>
<tr>
<td><code>&lt;</code></td>
<td>little-endian</td>
<td>standard</td>
<td>none</td>
</tr>
<tr>
<td><code>&gt;</code></td>
<td>big-endian</td>
<td>standard</td>
<td>none</td>
</tr>
<tr>
<td><code>!</code></td>
<td>network (=big-endian)</td>
<td>standard</td>
<td>none</td>
</tr>
</table>
'<code>@</code>' is currently not supported. Please use '<code>&lt;</code>'
or '<code>&gt;</code>'.<br>
<br>
<b>Format Characters</b><br>
<table border="1px solid black" summary="">
<tr>
<th scope="col">Format</th>
<th scope="col">C Type</th>
<th scope="col">Java Type</th>
<th scope="col">Standard size</th>
</tr>
<tr>
<td><code>x</code></td>
<td>pad byte</td>
</tr>
<tr>
<td><code>c</code></td>
<td>char</td>
<td>char</td>
<td>1</td>
</tr>
<tr>
<td><code>b</code></td>
<td>signed char</td>
<td>int</td>
<td>1</td>
</tr>
<tr>
<td><code>B</code></td>
<td>unsigned char</td>
<td>int</td>
<td>1</td>
</tr>
<tr>
<td><code>?</code></td>
<td>_Bool</td>
<td>boolean</td>
<td>1</td>
</tr>
<tr>
<td><code>h</code></td>
<td>short</td>
<td>int</td>
<td>2</td>
</tr>
<tr>
<td><code>H</code></td>
<td>unsigned short</td>
<td>int</td>
<td>2</td>
</tr>
<tr>
<td><code>i</code></td>
<td>int</td>
<td>int</td>
<td>4</td>
</tr>
<tr>
<td><code>I</code></td>
<td>unsigned int</td>
<td>long</td>
<td>4</td>
</tr>
<tr>
<td><code>l</code></td>
<td>long</td>
<td>int</td>
<td>4</td>
</tr>
<tr>
<td><code>L</code></td>
<td>unsigned long</td>
<td>long</td>
<td>4</td>
</tr>
<tr>
<td><code>q</code></td>
<td>long long</td>
<td>long</td>
<td>8</td>
</tr>
<tr>
<td><code>Q</code></td>
<td>unsigned long long</td>
<td>no support</td>
<td>8</td>
</tr>
<tr>
<td><code>n</code></td>
<td>ssize_t</td>
<td>no support</td>
</tr>
<tr>
<td><code>N</code></td>
<td>size_t</td>
<td>no support</td>
</tr>
<tr>
<td><code>e</code></td>
<td>half-precision float</td>
<td>no support</td>
</tr>
<tr>
<td><code>f</code></td>
<td>float</td>
<td>float</td>
<td>4</td>
</tr>
<tr>
<td><code>d</code></td>
<td>double</td>
<td>double</td>
<td>8</td>
</tr>
<tr>
<td><code>s</code></td>
<td>char[]</td>
<td>String</td>
</tr>
<tr>
<td><code>p</code></td>
<td>char[]</td>
<td>String</td>
</tr>
<tr>
<td><code>P</code></td>
<td>void *</td>
<td>no support</td>
</tr>
</table>
