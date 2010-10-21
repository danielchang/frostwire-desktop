/* 
 * Digital Audio Access Protocol (DAAP)
 * Copyright (C) 2004 Roger Kapsi, info at kapsi dot de
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package de.kapsi.net.daap.chunks.impl;

import de.kapsi.net.daap.chunks.ContainerChunk;

/**
 * The name is a bit misleading, it is NOT a java.util.Dictionary and
 * it has nothing to do with the java.util.Dictionary data structure.
 * This class is used to create a list of {@see de.kapsi.net.daap.chunks.ContentCode} 
 * capabilities (see Library for more information) of a DAAP server and
 * it describes essentially all the other Chunks in this package (their
 * contentCode, name and type).
 *
 * @author  Roger Kapsi
 */
public class Dictionary extends ContainerChunk {
    
    public Dictionary() {
        super("mdcl", "dmap.dictionary");
    }
}
