/* ------------------------------------------------------------------
 * Copyright (C) 1998-2009 PacketVideo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 * -------------------------------------------------------------------
 */
/*
 Filename: aac_mem_funcs.h
 Funtions:


------------------------------------------------------------------------------
 REVISION HISTORY


 Who:                                   Date: MM/DD/YYYY
 Description:
------------------------------------------------------------------------------


----------------------------------------------------------------------------
; CONTINUE ONLY IF NOT ALREADY DEFINED
----------------------------------------------------------------------------*/

#include <string.h>

#ifndef AAC_MEM_FUNCS_H
#define AAC_MEM_FUNCS_H

#define pv_memset(to, c, n)         memset(to, c, n)


#define pv_memcpy(to, from, n)      memcpy(to, from, n)
#define pv_memmove(to, from, n)     memmove(to, from, n)
#define pv_memcmp(p, q, n)          memcmp(p, q, n)



#endif
