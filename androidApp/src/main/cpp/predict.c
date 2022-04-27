/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 *
 * predict.c
 *
 * Code generation for function 'predict'
 *
 */

/* Include files */
#include "predict.h"
#include "CompactEnsemble.h"
#include "rt_nonfinite.h"

/* Function Definitions */
double predict(const double data[6])
{
  int i;
  boolean_T obj_IsCached[25];
  for (i = 0; i < 25; i++) {
    obj_IsCached[i] = false;
  }
  return CompactEnsemble_ensemblePredict(obj_IsCached, data);
}

/* End of code generation (predict.c) */
