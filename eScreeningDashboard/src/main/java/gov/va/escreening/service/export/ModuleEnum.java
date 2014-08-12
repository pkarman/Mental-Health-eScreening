package gov.va.escreening.service.export;

import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

enum ModuleCategory {
	SOCIAL, SERVICE, HEALTH_SYMPTOMS, HEALTH_FUNCTION, HEALTH_HABITS, PSYCHOLOGICAL_HEALTH
}

public enum ModuleEnum {

	ME_IDScreen(ModuleCategory.SOCIAL, "Identification", "ID of Veteran", null, "meIDScreen"), //
	ME_PresentingProblems(ModuleCategory.SOCIAL, "Presenting Problems", "OOO Presenting Problems", null, "mePresentingProblems"), //
	ME_BasicDemographics(ModuleCategory.SOCIAL, "Basic Demographics", "Basic Demographics", null, "meDemographics"), //
	ME_EduEmplIncome(ModuleCategory.SOCIAL, "Education, Employment & Income", "Education, Employment & Income", null, "meEduEmplIncome"), //
	ME_SocialEnvironment(ModuleCategory.SOCIAL, "Social Environment", "Social Environment", null, "meSocialEnvironment"), //
	ME_PromisEmotionalSupport(ModuleCategory.SOCIAL, "Promis Emotional Support", "Emotional Support", "PROMIS Emotional Support 4a", "mePromisEmotionalSupport"), //
	ME_HomelessnessReminder(ModuleCategory.SOCIAL, "Homelessness Clinical Reminder", "Unstable Housing", null, "meHomelessnessReminder"), //
	ME_PragmaticConcerns(ModuleCategory.SOCIAL, "Pragmatic Concerns", "legal concerns", null, "mePragmaticConcerns"), //
	ME_AdvanceDirective(ModuleCategory.SOCIAL, "Advance Directive", "Advance Directive", null, "meAdvanceDirective"), //
	ME_SpiritualHealth(ModuleCategory.SOCIAL, "Spiritual Health", "Spiritual Health", null, "meSpiritualHealth"), //

	ME_ServiceHistory(ModuleCategory.SERVICE, "Service History", "Service History", null, "meServiceHistory"), //
	ME_OEF_OIF_Reminder(ModuleCategory.SERVICE, "OEF OIF Clinical Reminder", "Most recent OEF/OIF/OND Combat deployment", null, "meOefOifReminder"), //
	ME_MilitaryDeploymentsHistory(ModuleCategory.SERVICE, "Military Deployments & History", "Military Deployments & History", null, "meMilitaryHistory"), //
	ME_Exposures(ModuleCategory.SERVICE, "Exposures", "environmental & combat exposures", null, "meExposures"), //
	ME_ServiceInjuries(ModuleCategory.SERVICE, "Service Injuries", "Injuries and when they occurred", null, "meServiceInjuries"), //

	ME_PHQ15(ModuleCategory.HEALTH_SYMPTOMS, "PHQ-15", "Somatic Symptoms", "PHQ-15", "mePHQ15"), //
	ME_OtherHealthSymptoms(ModuleCategory.HEALTH_SYMPTOMS, "Other Health Symptoms", "Physical health symptoms not measured by PHQ-15", null, "meOtherHealthSymptoms"), //
	ME_EmbeddedFragment(ModuleCategory.HEALTH_SYMPTOMS, "OOO Infect & Embedded Fragment CR", "Infections and Embedded Fragments", null, "meEmbeddedFragment"), //
	ME_BasicPain(ModuleCategory.HEALTH_SYMPTOMS, "Basic Pain", "Pain", null, "meBasicPain"), //
	ME_PromisPainIntensityInterference(ModuleCategory.HEALTH_SYMPTOMS, "Promis Pain Intensity & Interference", "Pain", "PROMIS Pain", "mePromisPainIntensityInterference"), //
	ME_Medications(ModuleCategory.HEALTH_SYMPTOMS, "Medications", "Medications", null, "meMedications"), //

	ME_PriorMHTreatment(ModuleCategory.HEALTH_FUNCTION, "Prior MH Treatment", "Prior Mental Health Treatment", null, "mePriorMentalHealthTreatment"), //
	ME_WHODAS_2_0(ModuleCategory.HEALTH_FUNCTION, "WHODAS 2.0", "Health Functioning", "WHODAS 2.0", "meWHODAS20"), //

	ME_Caffeine_Use(ModuleCategory.HEALTH_HABITS, "Caffeine Use", "Caffeine Use", null, "meCaffeineUse"), //
	ME_TobaccoCessationScreen(ModuleCategory.HEALTH_HABITS, "Tobacco Cessation Screen", "Tobacco Use", null, "meTobaccoUse"), //
	ME_AUDIT_C(ModuleCategory.HEALTH_HABITS, "AUDIT-C", "Alcohol Consumption", "AUDIT-C", "meAlcoholConsumption"), //
	ME_DAST_10(ModuleCategory.HEALTH_HABITS, "DAST-10", "Drug Abuse", null, "meDrugAbuse"), //

	ME_AvHallucinations(ModuleCategory.PSYCHOLOGICAL_HEALTH, "AV Hallucinations", "Audio/Visual Hallucinations", null, "meAvHallucinations"), //
	ME_BTBIS(ModuleCategory.PSYCHOLOGICAL_HEALTH, "BTBIS", "Traumatic Brain Injury", "BTBIS-Brief traumatic brain injury screener", "meBtbis"), //
	ME_PHQ9(ModuleCategory.PSYCHOLOGICAL_HEALTH, "PHQ-9", "Depression", "PHQ9", "meDepression"), //
	ME_MDQ(ModuleCategory.PSYCHOLOGICAL_HEALTH, "MDQ", "Mood Disorder", "MDQ", "meMoodDisorder"), //
	ME_MST(ModuleCategory.PSYCHOLOGICAL_HEALTH, "MST", "Military Sexual Trauma", "VA-MST", "meMilitarySexualTrauma"), //
	ME_GAD7Anxiety(ModuleCategory.PSYCHOLOGICAL_HEALTH, "GAD 7 Anxiety", "Anxiety", "GAD-7", "meAnxiety"), //
	ME_PCLC(ModuleCategory.PSYCHOLOGICAL_HEALTH, "PCL-C", "PTSD", "PCLC", "mePclc"), //
	ME_PCPTSD(ModuleCategory.PSYCHOLOGICAL_HEALTH, "PC-PTSD", "PTSD", "PC-PTSD, Primary Care PTSD screen", "mePcptsd"), //
	ME_ISI(ModuleCategory.PSYCHOLOGICAL_HEALTH, "ISI", "Insomnia", "ISI", "meInsomnia"), //
	ME_ROAS(ModuleCategory.PSYCHOLOGICAL_HEALTH, "ROAS", "Aggression", "ROAS", "meAggression"), //
	ME_CD_RISC_10(ModuleCategory.PSYCHOLOGICAL_HEALTH, "CD-RISC-10", "Resilience", "CD RISC10", "meResilience");

	private final ModuleCategory category;
	private final String moduleName;
	private final String description;
	private final String measureName;
	private final String moduleExporterName;

	private ModuleEnum(ModuleCategory category, String moduleName, String description, String measureName, String meResourceName) {
		this.category = category;
		this.moduleName = moduleName;
		this.description = description;
		this.measureName = measureName;
		this.moduleExporterName = meResourceName;
	}

	public ModuleCategory getCategory() {
		return category;
	}

	public String getModuleName() {
		return moduleName;
	}

	public String getDescription() {
		return description;
	}

	public String getMeasureName() {
		return measureName;
	}

	public List<DataExportCell> apply(BeanFactory bf,
			VeteranAssessment assessment, Integer identifiedExportType) {
		ModuleDataExporter mde = bf.getBean(moduleExporterName, ModuleDataExporter.class);

		return mde.apply(this, assessment, identifiedExportType);
	}
}
