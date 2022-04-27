/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 *
 * predictOneWithCache.c
 *
 * Code generation for function 'predictOneWithCache'
 *
 */

/* Include files */
#include "predictOneWithCache.h"
#include "CompactRegressionTree.h"
#include "rt_nonfinite.h"
#include "updateCache.h"
#include "rt_nonfinite.h"

/* Function Definitions */
double b_predictOneWithCache(const double X[6], double *cachedScore,
                             double *cachedWeights, const char combiner[15],
                             const double weak_learner_CutPredictorIndex[643],
                             const double weak_learner_Children[1286],
                             const double weak_learner_CutPoint[643],
                             const boolean_T weak_learner_NanCutPoints[643],
                             const double weak_learner_NodeMean[643],
                             boolean_T *cached)
{
  if (rtIsNaN(*cachedScore)) {
    *cachedScore = 0.0;
  }
  return updateCache(c_CompactRegressionTree_predict(
                         weak_learner_CutPredictorIndex, weak_learner_Children,
                         weak_learner_CutPoint, weak_learner_NanCutPoints,
                         weak_learner_NodeMean, X),
                     cachedScore, cachedWeights, cached, combiner);
}

double c_predictOneWithCache(const double X[6], double *cachedScore,
                             double *cachedWeights, const char combiner[15],
                             const double weak_learner_CutPredictorIndex[659],
                             const double weak_learner_Children[1318],
                             const double weak_learner_CutPoint[659],
                             const boolean_T weak_learner_NanCutPoints[659],
                             const double weak_learner_NodeMean[659],
                             boolean_T *cached)
{
  if (rtIsNaN(*cachedScore)) {
    *cachedScore = 0.0;
  }
  return updateCache(d_CompactRegressionTree_predict(
                         weak_learner_CutPredictorIndex, weak_learner_Children,
                         weak_learner_CutPoint, weak_learner_NanCutPoints,
                         weak_learner_NodeMean, X),
                     cachedScore, cachedWeights, cached, combiner);
}

double d_predictOneWithCache(const double X[6], double *cachedScore,
                             double *cachedWeights, const char combiner[15],
                             const double weak_learner_CutPredictorIndex[621],
                             const double weak_learner_Children[1242],
                             const double weak_learner_CutPoint[621],
                             const boolean_T weak_learner_NanCutPoints[621],
                             const double weak_learner_NodeMean[621],
                             boolean_T *cached)
{
  if (rtIsNaN(*cachedScore)) {
    *cachedScore = 0.0;
  }
  return updateCache(e_CompactRegressionTree_predict(
                         weak_learner_CutPredictorIndex, weak_learner_Children,
                         weak_learner_CutPoint, weak_learner_NanCutPoints,
                         weak_learner_NodeMean, X),
                     cachedScore, cachedWeights, cached, combiner);
}

double e_predictOneWithCache(const double X[6], double *cachedScore,
                             double *cachedWeights, const char combiner[15],
                             const double weak_learner_CutPredictorIndex[597],
                             const double weak_learner_Children[1194],
                             const double weak_learner_CutPoint[597],
                             const boolean_T weak_learner_NanCutPoints[597],
                             const double weak_learner_NodeMean[597],
                             boolean_T *cached)
{
  if (rtIsNaN(*cachedScore)) {
    *cachedScore = 0.0;
  }
  return updateCache(f_CompactRegressionTree_predict(
                         weak_learner_CutPredictorIndex, weak_learner_Children,
                         weak_learner_CutPoint, weak_learner_NanCutPoints,
                         weak_learner_NodeMean, X),
                     cachedScore, cachedWeights, cached, combiner);
}

double f_predictOneWithCache(const double X[6], double *cachedScore,
                             double *cachedWeights, const char combiner[15],
                             const double weak_learner_CutPredictorIndex[673],
                             const double weak_learner_Children[1346],
                             const double weak_learner_CutPoint[673],
                             const boolean_T weak_learner_NanCutPoints[673],
                             const double weak_learner_NodeMean[673],
                             boolean_T *cached)
{
  if (rtIsNaN(*cachedScore)) {
    *cachedScore = 0.0;
  }
  return updateCache(h_CompactRegressionTree_predict(
                         weak_learner_CutPredictorIndex, weak_learner_Children,
                         weak_learner_CutPoint, weak_learner_NanCutPoints,
                         weak_learner_NodeMean, X),
                     cachedScore, cachedWeights, cached, combiner);
}

double g_predictOneWithCache(const double X[6], double *cachedScore,
                             double *cachedWeights, const char combiner[15],
                             const double weak_learner_CutPredictorIndex[625],
                             const double weak_learner_Children[1250],
                             const double weak_learner_CutPoint[625],
                             const boolean_T weak_learner_NanCutPoints[625],
                             const double weak_learner_NodeMean[625],
                             boolean_T *cached)
{
  if (rtIsNaN(*cachedScore)) {
    *cachedScore = 0.0;
  }
  return updateCache(g_CompactRegressionTree_predict(
                         weak_learner_CutPredictorIndex, weak_learner_Children,
                         weak_learner_CutPoint, weak_learner_NanCutPoints,
                         weak_learner_NodeMean, X),
                     cachedScore, cachedWeights, cached, combiner);
}

double h_predictOneWithCache(const double X[6], double *cachedScore,
                             double *cachedWeights, const char combiner[15],
                             const double weak_learner_CutPredictorIndex[623],
                             const double weak_learner_Children[1246],
                             const double weak_learner_CutPoint[623],
                             const boolean_T weak_learner_NanCutPoints[623],
                             const double weak_learner_NodeMean[623],
                             boolean_T *cached)
{
  if (rtIsNaN(*cachedScore)) {
    *cachedScore = 0.0;
  }
  return updateCache(i_CompactRegressionTree_predict(
                         weak_learner_CutPredictorIndex, weak_learner_Children,
                         weak_learner_CutPoint, weak_learner_NanCutPoints,
                         weak_learner_NodeMean, X),
                     cachedScore, cachedWeights, cached, combiner);
}

double i_predictOneWithCache(const double X[6], double *cachedScore,
                             double *cachedWeights, const char combiner[15],
                             const double weak_learner_CutPredictorIndex[661],
                             const double weak_learner_Children[1322],
                             const double weak_learner_CutPoint[661],
                             const boolean_T weak_learner_NanCutPoints[661],
                             const double weak_learner_NodeMean[661],
                             boolean_T *cached)
{
  if (rtIsNaN(*cachedScore)) {
    *cachedScore = 0.0;
  }
  return updateCache(j_CompactRegressionTree_predict(
                         weak_learner_CutPredictorIndex, weak_learner_Children,
                         weak_learner_CutPoint, weak_learner_NanCutPoints,
                         weak_learner_NodeMean, X),
                     cachedScore, cachedWeights, cached, combiner);
}

double j_predictOneWithCache(const double X[6], double *cachedScore,
                             double *cachedWeights, const char combiner[15],
                             const double weak_learner_CutPredictorIndex[655],
                             const double weak_learner_Children[1310],
                             const double weak_learner_CutPoint[655],
                             const boolean_T weak_learner_NanCutPoints[655],
                             const double weak_learner_NodeMean[655],
                             boolean_T *cached)
{
  if (rtIsNaN(*cachedScore)) {
    *cachedScore = 0.0;
  }
  return updateCache(k_CompactRegressionTree_predict(
                         weak_learner_CutPredictorIndex, weak_learner_Children,
                         weak_learner_CutPoint, weak_learner_NanCutPoints,
                         weak_learner_NodeMean, X),
                     cachedScore, cachedWeights, cached, combiner);
}

double k_predictOneWithCache(const double X[6], double *cachedScore,
                             double *cachedWeights, const char combiner[15],
                             const double weak_learner_CutPredictorIndex[677],
                             const double weak_learner_Children[1354],
                             const double weak_learner_CutPoint[677],
                             const boolean_T weak_learner_NanCutPoints[677],
                             const double weak_learner_NodeMean[677],
                             boolean_T *cached)
{
  if (rtIsNaN(*cachedScore)) {
    *cachedScore = 0.0;
  }
  return updateCache(l_CompactRegressionTree_predict(
                         weak_learner_CutPredictorIndex, weak_learner_Children,
                         weak_learner_CutPoint, weak_learner_NanCutPoints,
                         weak_learner_NodeMean, X),
                     cachedScore, cachedWeights, cached, combiner);
}

double l_predictOneWithCache(const double X[6], double *cachedScore,
                             double *cachedWeights, const char combiner[15],
                             const double weak_learner_CutPredictorIndex[635],
                             const double weak_learner_Children[1270],
                             const double weak_learner_CutPoint[635],
                             const boolean_T weak_learner_NanCutPoints[635],
                             const double weak_learner_NodeMean[635],
                             boolean_T *cached)
{
  if (rtIsNaN(*cachedScore)) {
    *cachedScore = 0.0;
  }
  return updateCache(m_CompactRegressionTree_predict(
                         weak_learner_CutPredictorIndex, weak_learner_Children,
                         weak_learner_CutPoint, weak_learner_NanCutPoints,
                         weak_learner_NodeMean, X),
                     cachedScore, cachedWeights, cached, combiner);
}

double m_predictOneWithCache(const double X[6], double *cachedScore,
                             double *cachedWeights, const char combiner[15],
                             const double weak_learner_CutPredictorIndex[617],
                             const double weak_learner_Children[1234],
                             const double weak_learner_CutPoint[617],
                             const boolean_T weak_learner_NanCutPoints[617],
                             const double weak_learner_NodeMean[617],
                             boolean_T *cached)
{
  if (rtIsNaN(*cachedScore)) {
    *cachedScore = 0.0;
  }
  return updateCache(n_CompactRegressionTree_predict(
                         weak_learner_CutPredictorIndex, weak_learner_Children,
                         weak_learner_CutPoint, weak_learner_NanCutPoints,
                         weak_learner_NodeMean, X),
                     cachedScore, cachedWeights, cached, combiner);
}

double n_predictOneWithCache(const double X[6], double *cachedScore,
                             double *cachedWeights, const char combiner[15],
                             const double weak_learner_CutPredictorIndex[609],
                             const double weak_learner_Children[1218],
                             const double weak_learner_CutPoint[609],
                             const boolean_T weak_learner_NanCutPoints[609],
                             const double weak_learner_NodeMean[609],
                             boolean_T *cached)
{
  if (rtIsNaN(*cachedScore)) {
    *cachedScore = 0.0;
  }
  return updateCache(o_CompactRegressionTree_predict(
                         weak_learner_CutPredictorIndex, weak_learner_Children,
                         weak_learner_CutPoint, weak_learner_NanCutPoints,
                         weak_learner_NodeMean, X),
                     cachedScore, cachedWeights, cached, combiner);
}

double o_predictOneWithCache(const double X[6], double *cachedScore,
                             double *cachedWeights, const char combiner[15],
                             const double weak_learner_CutPredictorIndex[611],
                             const double weak_learner_Children[1222],
                             const double weak_learner_CutPoint[611],
                             const boolean_T weak_learner_NanCutPoints[611],
                             const double weak_learner_NodeMean[611],
                             boolean_T *cached)
{
  if (rtIsNaN(*cachedScore)) {
    *cachedScore = 0.0;
  }
  return updateCache(p_CompactRegressionTree_predict(
                         weak_learner_CutPredictorIndex, weak_learner_Children,
                         weak_learner_CutPoint, weak_learner_NanCutPoints,
                         weak_learner_NodeMean, X),
                     cachedScore, cachedWeights, cached, combiner);
}

double p_predictOneWithCache(const double X[6], double *cachedScore,
                             double *cachedWeights, const char combiner[15],
                             const double weak_learner_CutPredictorIndex[663],
                             const double weak_learner_Children[1326],
                             const double weak_learner_CutPoint[663],
                             const boolean_T weak_learner_NanCutPoints[663],
                             const double weak_learner_NodeMean[663],
                             boolean_T *cached)
{
  if (rtIsNaN(*cachedScore)) {
    *cachedScore = 0.0;
  }
  return updateCache(q_CompactRegressionTree_predict(
                         weak_learner_CutPredictorIndex, weak_learner_Children,
                         weak_learner_CutPoint, weak_learner_NanCutPoints,
                         weak_learner_NodeMean, X),
                     cachedScore, cachedWeights, cached, combiner);
}

double predictOneWithCache(const double X[6], double *cachedScore,
                           double *cachedWeights, const char combiner[15],
                           const double weak_learner_CutPredictorIndex[569],
                           const double weak_learner_Children[1138],
                           const double weak_learner_CutPoint[569],
                           const boolean_T weak_learner_NanCutPoints[569],
                           const double weak_learner_NodeMean[569],
                           boolean_T *cached)
{
  if (rtIsNaN(*cachedScore)) {
    *cachedScore = 0.0;
  }
  return updateCache(b_CompactRegressionTree_predict(
                         weak_learner_CutPredictorIndex, weak_learner_Children,
                         weak_learner_CutPoint, weak_learner_NanCutPoints,
                         weak_learner_NodeMean, X),
                     cachedScore, cachedWeights, cached, combiner);
}

double q_predictOneWithCache(const double X[6], double *cachedScore,
                             double *cachedWeights, const char combiner[15],
                             const double weak_learner_CutPredictorIndex[657],
                             const double weak_learner_Children[1314],
                             const double weak_learner_CutPoint[657],
                             const boolean_T weak_learner_NanCutPoints[657],
                             const double weak_learner_NodeMean[657],
                             boolean_T *cached)
{
  if (rtIsNaN(*cachedScore)) {
    *cachedScore = 0.0;
  }
  return updateCache(r_CompactRegressionTree_predict(
                         weak_learner_CutPredictorIndex, weak_learner_Children,
                         weak_learner_CutPoint, weak_learner_NanCutPoints,
                         weak_learner_NodeMean, X),
                     cachedScore, cachedWeights, cached, combiner);
}

double r_predictOneWithCache(const double X[6], double *cachedScore,
                             double *cachedWeights, const char combiner[15],
                             const double weak_learner_CutPredictorIndex[593],
                             const double weak_learner_Children[1186],
                             const double weak_learner_CutPoint[593],
                             const boolean_T weak_learner_NanCutPoints[593],
                             const double weak_learner_NodeMean[593],
                             boolean_T *cached)
{
  if (rtIsNaN(*cachedScore)) {
    *cachedScore = 0.0;
  }
  return updateCache(s_CompactRegressionTree_predict(
                         weak_learner_CutPredictorIndex, weak_learner_Children,
                         weak_learner_CutPoint, weak_learner_NanCutPoints,
                         weak_learner_NodeMean, X),
                     cachedScore, cachedWeights, cached, combiner);
}

double s_predictOneWithCache(const double X[6], double *cachedScore,
                             double *cachedWeights, const char combiner[15],
                             const double weak_learner_CutPredictorIndex[559],
                             const double weak_learner_Children[1118],
                             const double weak_learner_CutPoint[559],
                             const boolean_T weak_learner_NanCutPoints[559],
                             const double weak_learner_NodeMean[559],
                             boolean_T *cached)
{
  if (rtIsNaN(*cachedScore)) {
    *cachedScore = 0.0;
  }
  return updateCache(t_CompactRegressionTree_predict(
                         weak_learner_CutPredictorIndex, weak_learner_Children,
                         weak_learner_CutPoint, weak_learner_NanCutPoints,
                         weak_learner_NodeMean, X),
                     cachedScore, cachedWeights, cached, combiner);
}

double t_predictOneWithCache(const double X[6], double *cachedScore,
                             double *cachedWeights, const char combiner[15],
                             const double weak_learner_CutPredictorIndex[585],
                             const double weak_learner_Children[1170],
                             const double weak_learner_CutPoint[585],
                             const boolean_T weak_learner_NanCutPoints[585],
                             const double weak_learner_NodeMean[585],
                             boolean_T *cached)
{
  if (rtIsNaN(*cachedScore)) {
    *cachedScore = 0.0;
  }
  return updateCache(u_CompactRegressionTree_predict(
                         weak_learner_CutPredictorIndex, weak_learner_Children,
                         weak_learner_CutPoint, weak_learner_NanCutPoints,
                         weak_learner_NodeMean, X),
                     cachedScore, cachedWeights, cached, combiner);
}

/* End of code generation (predictOneWithCache.c) */
