package gov.va.escreening.templateprocessor;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import gov.va.escreening.test.TestAssessmentVariableBuilder;
import gov.va.escreening.test.TestAssessmentVariableBuilder.TableQuestionAvBuilder;
import gov.va.escreening.variableresolver.CustomAssessmentVariableResolver;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/**
 * This class is concerned with testing Freemarker functions used for Assessment Variable transformations<br/>
 * This is an integration test.<br/>
 * 
 * @author Robin Carnow
 *
 */
@RunWith(JUnit4.class)
public class TransformationFreeMarkerFunctionTest extends FreeMarkerFunctionTest{
    private static DateTimeFormatter STANDARD_DATE_FORMAT = DateTimeFormat.forPattern("MM/dd/yyyy");

    @Test
    public void testGetResponseForFreeText() throws Exception{
        assertEquals("Bill", 
                render("${getResponse(var1, 1)}",
                        avBuilder.addFreeTextAV(1, "name", "Bill")));
    }

    @Test
    public void testDelimitTransformationForAppointments() throws Exception {
        Date firstApptTime = new DateTime().plusDays(5).toDate();
        Date secondApptTime = new DateTime().plusDays(6).toDate();

        assertEquals("*" + CustomAssessmentVariableResolver.APPOINTMENT_DATE_FORMAT.format(firstApptTime) + "Clinic 1-**" 
                + CustomAssessmentVariableResolver.APPOINTMENT_DATE_FORMAT.format(secondApptTime) + "Clinic 2-", 

                render("${delimit(var6,\"*\",\"**\",\"-\",true,\"defaultVal\")}",
                        avBuilder
                        .addCustomAV(CustomAssessmentVariableResolver.CUSTOM_VETERAN_APPOINTMENTS)
                            .addAppointment("Clinic 1", firstApptTime, "active")
                            .addAppointment("Clinic 2", secondApptTime, "active")));
    }

    @Test
    public void testDelimitTransformationForAppointmentsNoResponseGiven() throws Exception {
        //test that transformation still works when no veteran response is available
        assertEquals("defaultVaLL", 
                render("${delimit(var6,\"*\",\"**\",\"-\",true,\"defaultVaLL\")}",
                        avBuilder
                        .addCustomAV(CustomAssessmentVariableResolver.CUSTOM_VETERAN_APPOINTMENTS)));
    }

    @Test
    public void testDelimitTransformationForSelectMulti() throws Exception{
        assertEquals("*first-*third-**fourth-", 
                render("${delimit(var1,\"*\",\"**\",\"-\",true,\"defaultVaLL\")}",
                        avBuilder
                        .addSelectMultiAV(1, null)
                            .addAnswer("first", true, null)
                            .addAnswer("second", false, null)
                            .addAnswer("third", true, null)
                            .addAnswer("fourth", true, null)));
    }

    @Test
    public void testDelimitTransformationForMultiSelectWithOtherAnswer() throws Exception{
        String otherResponse = "my other answer";

        assertEquals("*first-*"+ otherResponse + "-**fourth-", 
                render("${delimit(var1,\"*\",\"**\",\"-\",true,\"defaultVaLL\")}",
                        avBuilder
                        .addSelectMultiAV(1, null)
                            .addAnswer("first", true, null)
                            .addAnswer("second", false, null)
                            .addAnswer("third", true, otherResponse)
                            .addAnswer("fourth", true, null)));
    }

    @Test
    public void testDelimitTransformationForSelectMultiNoResponseGiven() throws Exception{
        //test that transformation still works when no veteran response is available
        assertEquals("defaultVaLL", 
                render("${delimit(var1,\"*\",\"**\",\"-\",true,\"defaultVaLL\")}",
                        avBuilder
                        .addSelectMultiAV(1, null)
                            .addAnswer("first", null, null)
                            .addAnswer("second", null, null)
                            .addAnswer("third", null, null)
                            .addAnswer("fourth", null, null)));
    }

    @Test
    public void testDelimitTransformationForSingleValueCase() throws Exception{
        //when there is only one value the prefix, lastPrefix, and suffix should never be returned
        assertEquals("third", 
                render("${delimit(var1,\"*\",\"**\",\"-\",true,\"defaultVaLL\")}",
                        avBuilder
                        .addSelectMultiAV(1, null)
                            .addAnswer("first", false, null)
                            .addAnswer("second", false, null)
                            .addAnswer("third", true, null)
                            .addAnswer("fourth", false, null)));
    }

    @Test
    public void testDelimitTranslationForSuffixAfterLastEntry() throws Exception{
        assertEquals("*first-**second", 
                render("${delimit(var1,\"*\",\"**\",\"-\",false,\"defaultVaLL\")}",
                        avBuilder
                        .addSelectMultiAV(1, null)
                            .addAnswer("first", true, null)
                            .addAnswer("second",true, null)));
    }

    /**    TESTS for yearsFromDate transformation  **/

    //Transformation takes value and emits the number of years between the veteran entered date and the assessment date

    @Test
    public void testYearsFromDateTranslationSameDay() throws Exception{
        DateTime date = new DateTime().minusYears(3);

        assertEquals("3", 
                render("${yearsFromDate(var1)}",
                        avBuilder
                        .addFreeTextAV(1, "dob", STANDARD_DATE_FORMAT.print(date))
                            .setDataTypeValidation("date")));
    }

    @Test
    public void testYearsFromDateTranslationDayBefore() throws Exception{
        //test before today's date which should count current year 
        DateTime date = new DateTime().minusYears(3).minusDays(1);

        assertEquals("3", 
                render("${yearsFromDate(var1)}",
                        avBuilder
                        .addFreeTextAV(1, "dob", STANDARD_DATE_FORMAT.print(date))
                            .setDataTypeValidation("date")));
    }

    @Test
    public void testYearsFromDateTranslationDayAfter() throws Exception{
        //after today's date which should not count last year as complete
        DateTime date = new DateTime().minusYears(3).plusDays(1);

        assertEquals("2", 
                render("${yearsFromDate(var1)}", 
                        avBuilder
                        .addFreeTextAV(1, "dob", STANDARD_DATE_FORMAT.print(date))
                            .setDataTypeValidation("date")));
    }

    @Test
    public void testYearsFromDateTransformationNoResponseGiven() throws Exception{
        //the freemarker function should gracefully deal with not having an answer
        assertEquals(TestAssessmentVariableBuilder.DEFAULT_VALUE, 
                render("${yearsFromDate(var1)}",
                        avBuilder
                        .addFreeTextAV(1, "dob", null)
                            .setDataTypeValidation("date")));
    }

    /** TESTS for delimitedMatrixQuestions translation Select-One and Select-Multi Matrix Questions  **/

    @Test
    public void testDelimitedMatrixQuestionsTranslationForSelectOneMatrix() throws Exception{
        List<Integer> columnAvList = avBuilder
                .addSelectOneMatrixAV(123, "test matrix question")
                    .addChildWithMeasureId(111, "q1")
                    .addChildWithMeasureId(222, "q2")
                    .addChildWithMeasureId(333, "q3")
                    .addColumn(null, null, null, ImmutableList.of(true, false, false), null)
                    .addColumn(null, null, null, ImmutableList.of(false, true, false), null)
                    .addColumn(null, null, null, ImmutableList.of(false, false, true), null)
                    .getColumnAnswerIds(0, 1); //we select the first two columns as columns we want the veteran to select


        //the map we give maps from the child questions we want to possibly show to the text we want to emit
        StringBuilder templateText = new StringBuilder("${delimitedMatrixQuestions(var123,{\"111\":\"first question\",\"222\":\"second question\"},[");
        templateText.append(Joiner.on(",").skipNulls().join(columnAvList)).append("])}");

        assertEquals("first question, and second question", render(templateText.toString(), avBuilder));
    }

    @Test
    public void testDelimitedMatrixQuestionsTranslationForSelectOneMatrixTestSkippingOfUnselectedAnswers() throws Exception{
        List<Integer> columnAvList = avBuilder
                .addSelectOneMatrixAV(123, "test matrix question")
                    .addChildWithMeasureId(111, "q1")
                    .addChildWithMeasureId(222, "q2")
                    .addChildWithMeasureId(333, "q3")
                    .addColumn(null, null, null, ImmutableList.of(false, false, false), null)
                    .addColumn(null, null, null, ImmutableList.of(false, true, false), null)
                    .addColumn(null, null, null, ImmutableList.of(false, false, true), null)
                    .getColumnAnswerIds(0, 1); //we select the first two columns as columns we want the veteran to select


        //the map we give maps from the child questions we want to possibly show to the text we want to emit
        StringBuilder templateText = new StringBuilder("${delimitedMatrixQuestions(var123,{\"111\":\"first question\",\"222\":\"second question\"},[");
        templateText.append(Joiner.on(",").skipNulls().join(columnAvList)).append("])}");

        assertEquals("second question", render(templateText.toString(), avBuilder));
    }

    @Test
    public void testDelimitedMatrixQuestionsTranslationForSelectMultiMatrix() throws Exception{
        List<Integer> columnAnswerIdList = avBuilder
                .addSelectMultiMatrixAV(123, "test matrix question")
                    .addChildWithMeasureId(111, "q1")
                    .addChildWithMeasureId(222, "q2")
                    .addChildWithMeasureId(333, "q3")
                    .addColumn(null, null, null, ImmutableList.of(true, false, false), null)
                    .addColumn(null, null, null, ImmutableList.of(false, true, true), null)
                    .addColumn(null, null, null, ImmutableList.of(false, true, false), null)
                    .getColumnAnswerIds(0, 1); //we select the first two columns as columns we want the veteran to select

        //the map we give maps from the child questions we want to possibly show to the text we want to emit
        StringBuilder templateText = new StringBuilder("${delimitedMatrixQuestions(var123,{\"111\":\"first question\",\"222\":\"second question\"},[");
        templateText.append(Joiner.on(",").skipNulls().join(columnAnswerIdList)).append("])}");

        assertEquals("first question, and second question", render(templateText.toString(), avBuilder));
    }

    @Test
    public void testDelimitedMatrixQuestionsTranslationForSelectOneMatrix_SingleValueOutputCase() throws Exception{
        List<Integer> columnAvList = avBuilder
                .addSelectOneMatrixAV(123, "test matrix question")
                    .addChildWithMeasureId(111, "q1")
                    .addChildWithMeasureId(222, "q2")
                    .addChildWithMeasureId(333, "q3")
                    .addColumn(null, null, null, ImmutableList.of(false, false, false), null)
                    .addColumn(null, null, null, ImmutableList.of(false, true, false), null)
                    .addColumn(null, null, null, ImmutableList.of(false, false, false), null)
                    .getColumnAnswerIds(1); //we select the second column as the one we want the veteran to select


        //the map we give maps from the child questions we want to possibly show to the text we want to emit
        StringBuilder templateText = new StringBuilder("${delimitedMatrixQuestions(var123,{\"222\":\"second question\"},[");
        templateText.append(Joiner.on(",").skipNulls().join(columnAvList)).append("])}");

        assertEquals("second question", render(templateText.toString(), avBuilder));
    }

    @Test
    public void testDelimitedMatrixQuestionsTranslationForSelectMultiMatrix_SingleValueOutputCase() throws Exception{
        List<Integer> columnAvList = avBuilder
                .addSelectMultiMatrixAV(123, "test matrix question")
                    .addChildWithMeasureId(111, "q1")
                    .addChildWithMeasureId(222, "q2")
                    .addChildWithMeasureId(333, "q3")
                    .addColumn("col1", null, null, ImmutableList.of(false, false, false), null)
                    .addColumn("col2", null, null, ImmutableList.of(false, true, false), null)
                    .addColumn("col3", null, null, ImmutableList.of(false, false, false), null)
                    .getColumnAnswerIds(1); //we select the second column as the one we want the veteran to select

        //the map we give maps from the child questions we want to possibly show to the text we want to emit
        StringBuilder templateText = new StringBuilder("${delimitedMatrixQuestions(var123,{\"222\":\"second question\"},[");
        templateText.append(Joiner.on(",").skipNulls().join(columnAvList)).append("])}");

        assertEquals("second question", render(templateText.toString(), avBuilder));
    }


    @Test
    public void testDelimitedMatrixQuestionsTranslationForSelectOneMatrix_NoResponseGiven() throws Exception{
        //test that transformation still works when no veteran response is available
        List<Integer> columnAvList = avBuilder
                .addSelectOneMatrixAV(123, "test matrix question")
                    .addChildWithAvId(111, "q1")
                    .addChildWithAvId(222, "q2")
                    .addChildWithAvId(333, "q3")
                    .addColumn(null, null, null, null, null)
                    .addColumn(null, null, null, null, null)
                    .addColumn(null, null, null, null, null)
                    .getColumnAvs(1); //we select the second column as the one we want the veteran to select


        //the map we give maps from the child questions we want to possibly show to the text we want to emit
        StringBuilder templateText = new StringBuilder("${delimitedMatrixQuestions(var123,{\"222\":\"second question\"},[");
        templateText.append(Joiner.on(",").skipNulls().join(columnAvList)).append("])}");

        assertEquals(TestAssessmentVariableBuilder.DEFAULT_VALUE, render(templateText.toString(), avBuilder));
    }

    @Test
    public void testDelimitedMatrixQuestionsTranslationForSelectMultiMatrix_NoResponseGiven() throws Exception{
        //test that transformation still works when no veteran response is available
        List<Integer> columnAvList = avBuilder
                .addSelectMultiMatrixAV(123, "test matrix question")
                    .addChildWithAvId(111, "q1")
                    .addChildWithAvId(222, "q2")
                    .addChildWithAvId(333, "q3")
                    .addColumn(null, null, null, null, null)
                    .addColumn(null, null, null, null, null)
                    .addColumn(null, null, null, null, null)
                    .getColumnAvs(1); //we select the second column as the one we want the veteran to select


        //the map we give maps from the child questions we want to possibly show to the text we want to emit
        StringBuilder templateText = new StringBuilder("${delimitedMatrixQuestions(var123,{\"222\":\"second question\"},[");
        templateText.append(Joiner.on(",").skipNulls().join(columnAvList)).append("])}");

        assertEquals(TestAssessmentVariableBuilder.DEFAULT_VALUE, render(templateText.toString(), avBuilder));
    }

    /** TESTS for numberOfEntries translation for table questions  **/

    @Test
    public void testNumberOfEntriesTranslation() throws Exception{
        assertEquals("3", 
                render("${numberOfEntries(var1)}",
                        avBuilder
                        .addTableQuestionAv(1, null, false, null)
                            .addChildFreeText(null, "free text question", Lists.newArrayList("first", null, null))
                            .addChildSelectOneAv(null, "select one question")
                                .addChildSelectAnswer("answer 1", Lists.newArrayList(true, false, false), null)
                                .addChildSelectAnswer("answer 2", Lists.newArrayList(false, true, true), null)
                            .addChildSelectMultiAv(null, "select multi question")
                                .addChildSelectAnswer(null, Lists.newArrayList(false, false, false), null)
                                .addChildSelectAnswer(null, Lists.newArrayList(false, false, false), null)));

        assertEquals("4", 
                render("${numberOfEntries(var1)}",
                        avBuilder
                        .addTableQuestionAv(1, null, false, null)
                            .addChildFreeText(null, "free text question", Lists.newArrayList("first", "second", "third", "fourth"))
                            .addChildSelectOneAv(null, "select one question")
                                .addChildSelectAnswer("answer 1", Lists.newArrayList(true, false, false), null)
                                .addChildSelectAnswer("answer 2", Lists.newArrayList(false, true, true), null)
                            .addChildSelectMultiAv(null, "select multi question")
                                .addChildSelectAnswer(null, Lists.newArrayList(false, false, false), null)
                                .addChildSelectAnswer(null, Lists.newArrayList(false, false, false), null)));
    }

    @Test
    public void testNumberOfEntriesTranslationNoResponseGiven() throws Exception{
        //test that transformation still works when no veteran response is available
        assertEquals("0", 
                render("${numberOfEntries(var1)}",
                        avBuilder
                        .addTableQuestionAv(1, null, false, null)
                            .addChildFreeText(null, "free text question", null)
                            .addChildSelectOneAv(null, "select one question")
                                .addChildSelectAnswer("answer 1", null, null)
                                .addChildSelectAnswer("answer 2", null, null)
                            .addChildSelectMultiAv(null, "select multi question")
                                .addChildSelectAnswer(null, null, null)
                                .addChildSelectAnswer(null, null, null)));
    }

    /** TESTS for delimitTableField translation for table questions  **/
    // delimitTableField table=DEFAULT_VALUE childAvId=DEFAULT_VALUE prefix='' lastPrefix='' suffix='' includeSuffixAtEnd=true defaultValue=DEFAULT_VALUE
    @Test
    public void testDelimitTableFieldTranslationWithDefaultParameters() throws Exception{
        TableQuestionAvBuilder tableBuilder = avBuilder
                .addTableQuestionAv(1, null, false, null)
                    .addChildFreeText(null, "free text question", Lists.newArrayList("first", "second", "third", "fourth"))
                    .addChildSelectOneAv(null, "select one question")
                        .addChildSelectAnswer("answer 1", Lists.newArrayList(false, false, true), null)
                        .addChildSelectAnswer("answer 2", Lists.newArrayList(false, true, false), null)
                        .addChildSelectAnswer("answer 3", Lists.newArrayList(true, false, false), null);

        //grab the select one's measure ID which we will be delimiting
        Integer childMeasureId = tableBuilder.getCurrentChildBuilder().getMeasure().getMeasureId();

        tableBuilder.addChildSelectMultiAv(null, "select multi question")
        .addChildSelectAnswer(null, Lists.newArrayList(true, true, true), null)
        .addChildSelectAnswer(null, Lists.newArrayList(false, true, false), null);


        assertEquals("answer 3, answer 2, and answer 1", 
                render("${delimitTableField(var1," + childMeasureId + ")}",
                        avBuilder));
    }

    @Test
    public void testDelimitTableFieldTranslationWithPassedInParameters() throws Exception{
        TableQuestionAvBuilder tableBuilder = avBuilder
                .addTableQuestionAv(1, null, false, null)
                    .addChildFreeText(null, "free text question", Lists.newArrayList("first", "second", "third", "fourth"))
                    .addChildSelectOneAv(null, "select one question")
                        .addChildSelectAnswer("answer 1", Lists.newArrayList(false, false, true), null)
                        .addChildSelectAnswer("answer 2", Lists.newArrayList(false, true, false), null)
                        .addChildSelectAnswer("answer 3", Lists.newArrayList(true, false, false), null);

        //grab the select one's AV which we will be delimiting
        Integer childMeasureId = tableBuilder.getCurrentChildBuilder().getMeasure().getMeasureId();

        tableBuilder
            .addChildSelectMultiAv(null, "select multi question")
                .addChildSelectAnswer(null, Lists.newArrayList(true, true, true), null)
                .addChildSelectAnswer(null, Lists.newArrayList(false, true, false), null);

        assertEquals("*answer 3}*answer 2}**answer 1}", 
                render("${delimitTableField(var1," + childMeasureId + ", \"*\", \"**\", \"}\", true, \"my default!\")}",
                        avBuilder));

        assertEquals("*answer 3}*answer 2}**answer 1", 
                render("${delimitTableField(var1," + childMeasureId + ", \"*\", \"**\", \"}\", false, \"my default!\")}",
                        avBuilder));

        assertEquals("*answer 3}*answer 2}@answer 1", 
                render("${delimitTableField(var1," + childMeasureId + ", \"*\", \"@\", \"}\", false, \"my default!\")}",
                        avBuilder));
    }

    @Test
    public void testDelimitTableFieldTranslationNoResponseGiven() throws Exception{
        //test that transformation still works when no veteran response is available
        TableQuestionAvBuilder tableBuilder = avBuilder
                .addTableQuestionAv(1, null, false, null)
                    .addChildFreeText(null, "free text question", Lists.newArrayList("first", "second", "third", "fourth"))
                    .addChildSelectOneAv(null, "select one question")
                        .addChildSelectAnswer("answer 1", null, null)
                        .addChildSelectAnswer("answer 2", null, null)
                        .addChildSelectAnswer("answer 3", null, null);

        //grab the select one's AV which we will be delimiting
        Integer childAvId = tableBuilder.getCurrentChildBuilder().getMeasure().getAssessmentVariable().getAssessmentVariableId();

        tableBuilder.addChildSelectMultiAv(null, "select multi question")
        .addChildSelectAnswer(null, Lists.newArrayList(true, true, true), null)
        .addChildSelectAnswer(null, Lists.newArrayList(false, true, false), null);

        assertEquals("my default!", 
                render("${delimitTableField(var1," + childAvId + ", \"*\", \"@\", \"}\", false, \"my default!\")}",
                        avBuilder));

        assertEquals(TestAssessmentVariableBuilder.DEFAULT_VALUE, 
                render("${delimitTableField(var1," + childAvId + ")}",
                        avBuilder));
    }

    @Test
    public void testGetTableVariableFreeMarkerFunction() throws Exception{
        TableQuestionAvBuilder tableBuilder = avBuilder
                .addTableQuestionAv(1, null, false, null)
                .addChildFreeText(null, "free text question", Lists.newArrayList("first", "second", "third", "fourth"));

        Integer freeTextChildAvId = tableBuilder.getCurrentChildBuilder().getMeasure().getAssessmentVariable().getAssessmentVariableId();

        tableBuilder
            .addChildSelectOneAv(null, "select one question")
                .addChildSelectAnswer("answer 1", Lists.newArrayList(false, false, true), null)
                .addChildSelectAnswer("answer 2", Lists.newArrayList(false, true, false), null)
                .addChildSelectAnswer("answer 3", Lists.newArrayList(true, false, false), null);

        Integer selectOneChildAvId = tableBuilder.getCurrentChildBuilder().getMeasure().getAssessmentVariable().getAssessmentVariableId();


        tableBuilder
            .addChildSelectMultiAv(null, "select multi question")
                .addChildSelectAnswer("answer 1", Lists.newArrayList(true, true, false), null)
                .addChildSelectAnswer("answer 2", Lists.newArrayList(false, true, true), null);

        Integer selectMultiChildAvId = tableBuilder.getCurrentChildBuilder().getMeasure().getAssessmentVariable().getAssessmentVariableId();

        //test freetext for each row value
        assertEquals("first", 
                render("<#assign tableHash = createTableHash(var1)>${getResponse(getTableVariable(tableHash,var" + freeTextChildAvId + ",0))}",
                        avBuilder));

        assertEquals("second", 
                render("<#assign tableHash = createTableHash(var1)>${getResponse(getTableVariable(tableHash,var" + freeTextChildAvId + ",1))}",
                        avBuilder));

        assertEquals("third", 
                render("<#assign tableHash = createTableHash(var1)>${getResponse(getTableVariable(tableHash,var" + freeTextChildAvId + ",2))}",
                        avBuilder));

        assertEquals("fourth", 
                render("<#assign tableHash = createTableHash(var1)>${getResponse(getTableVariable(tableHash,var" + freeTextChildAvId + ",3))}",
                        avBuilder));
        //test when it doesn't exist
        assertEquals(TestAssessmentVariableBuilder.DEFAULT_VALUE, 
                render("<#assign tableHash = createTableHash(var1)>${getResponse(getTableVariable(tableHash,var" + freeTextChildAvId + ",4))}",
                        avBuilder));

        // test select one for each row
        assertEquals("answer 3", 
                render("<#assign tableHash = createTableHash(var1)>${getResponse(getTableVariable(tableHash,var" + selectOneChildAvId + ",0))}",
                        avBuilder));

        assertEquals("answer 2", 
                render("<#assign tableHash = createTableHash(var1)>${getResponse(getTableVariable(tableHash,var" + selectOneChildAvId + ",1))}",
                        avBuilder));

        assertEquals("answer 1", 
                render("<#assign tableHash = createTableHash(var1)>${getResponse(getTableVariable(tableHash,var" + selectOneChildAvId + ",2))}",
                        avBuilder));

        //test when it doesn't exist
        assertEquals(TestAssessmentVariableBuilder.DEFAULT_VALUE, 
                render("<#assign tableHash = createTableHash(var1)>${getResponse(getTableVariable(tableHash,var" + selectOneChildAvId + ",20))}",
                        avBuilder));

        //test select multi
        assertEquals("answer 1", 
                render("<#assign tableHash = createTableHash(var1)>${getResponse(getTableVariable(tableHash,var" + selectMultiChildAvId + ",0))}",
                        avBuilder));

        assertEquals("answer 1, and answer 2", 
                render("<#assign tableHash = createTableHash(var1)>${getResponse(getTableVariable(tableHash,var" + selectMultiChildAvId + ",1))}",
                        avBuilder));

        assertEquals("answer 2", 
                render("<#assign tableHash = createTableHash(var1)>${getResponse(getTableVariable(tableHash,var" + selectMultiChildAvId + ",2))}",
                        avBuilder));

        //test when child ID doesn't exist
        assertEquals(TestAssessmentVariableBuilder.DEFAULT_VALUE, 
                render("<#assign tableHash = createTableHash(var1)>${getResponse(getTableVariable(tableHash,var" + 99999999 + ",0))}",
                        avBuilder));
    }
}
