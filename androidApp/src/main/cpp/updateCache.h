/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 *
 * updateCache.h
 *
 * Code generation for function 'updateCache'
 *
 */

#ifndef UPDATECACHE_H
#define UPDATECACHE_H

/* Include files */
#include "rtwtypes.h"
#include <stddef.h>
#include <stdlib.h>

#ifdef __cplusplus
extern "C" {
#endif

/* Function Declarations */
double updateCache(double learnerscore, double *cachedScore,
                   double *cachedWeights, boolean_T *cached,
                   const char combinerName[15]);

#ifdef __cplusplus
}
#endif

#endif
/* End of code generation (updateCache.h) */
