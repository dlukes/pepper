package org.corpus_tools.pepper.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.corpus_tools.pepper.common.FormatDesc;
import org.corpus_tools.pepper.common.ModuleFitness;
import org.corpus_tools.pepper.common.ModuleFitness.Fitness;
import org.corpus_tools.pepper.common.ModuleFitness.FitnessFeature;
import org.corpus_tools.pepper.common.Pepper;
import org.corpus_tools.pepper.exceptions.PepperFWException;
import org.corpus_tools.pepper.impl.IntegrationTestDesc;
import org.corpus_tools.pepper.modules.PepperExporter;
import org.corpus_tools.pepper.modules.PepperImporter;
import org.corpus_tools.pepper.modules.PepperModule;
import org.eclipse.emf.common.util.URI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ModuleFitnessCheckerTest {

	private PepperImporter createFitImporter() {
		PepperImporter fitModule = mock(PepperImporter.class);
		when(fitModule.isReadyToStart()).thenReturn(true);
		when(fitModule.getName()).thenReturn("MyImporter");

		when(fitModule.getSupplierContact()).thenReturn(URI.createURI("me@mail.com"));
		when(fitModule.getSupplierHomepage()).thenReturn(URI.createURI("http//me.com"));
		when(fitModule.getDesc()).thenReturn("any description");
		when(fitModule.isImportable(any(URI.class))).thenReturn(1.0);
		when(fitModule.getSupportedFormats()).thenReturn(Arrays.asList(new FormatDesc().setFormatName("format")));
		return fitModule;
	}

	private PepperModule createHealthyModule() {
		PepperModule healthyModule = mock(PepperImporter.class);
		when(healthyModule.getName()).thenReturn("MyModule");
		when(healthyModule.getDesc()).thenReturn("any description");
		when(healthyModule.getSupplierHomepage()).thenReturn(null);
		when(healthyModule.getSupplierContact()).thenReturn(URI.createURI("me@mail.com"));
		when(healthyModule.isReadyToStart()).thenReturn(true);
		return healthyModule;
	}

	private PepperModule createCriticalModule() {
		PepperModule criticalModule = mock(PepperImporter.class);
		when(criticalModule.getSupplierHomepage()).thenReturn(URI.createURI("http://me.com"));
		when(criticalModule.getSupplierContact()).thenReturn(URI.createURI("me@mail.com"));
		when(criticalModule.isReadyToStart()).thenReturn(false);
		return criticalModule;
	}

	@Test
	public void whenCheckingHealthForNull_thenReturnNull() {
		PepperModule module = null;
		assertThat(ModuleFitnessChecker.checkHealth(module)).isNull();
	}

	@Test
	public void whenModuleIsReadyToRun_thenCorrespondingHealthFeatureShouldBeTrue() {
		PepperModule module = mock(PepperModule.class);
		when(module.isReadyToStart()).thenReturn(true);
		assertThat(ModuleFitnessChecker.checkHealth(module).getFitness(FitnessFeature.IS_READY_TO_RUN)).isEqualTo(true);
	}

	@Test
	public void whenModuleIsNotReadyToRun_thenCorrespondingHealthFeatureShouldBeTrue() {
		PepperModule module = mock(PepperModule.class);
		when(module.isReadyToStart()).thenReturn(false);
		assertThat(ModuleFitnessChecker.checkHealth(module).getFitness(FitnessFeature.IS_READY_TO_RUN)).isEqualTo(false);
	}

	@Test
	public void whenCheckingHealthForNullSet_thenReturnEmptyList() {
		Collection<PepperModule> modules = null;
		ModuleFitnessChecker.checkHealth(modules).isEmpty();
	}

	@Test
	public void whenCheckingHealthForMultipleModules_thenReturnListOfFitnessValues() {
		PepperModule healthyModule = createHealthyModule();
		PepperModule criticalModule = createCriticalModule();

		List<ModuleFitness> fitnesses = ModuleFitnessChecker.checkHealth(Arrays.asList(healthyModule, criticalModule));
		assertThat(fitnesses.get(0).getOverallFitness()).isEqualTo(Fitness.HEALTHY);
		assertThat(fitnesses.get(1).getOverallFitness()).isEqualTo(Fitness.CRITICAL);
	}

	@Test
	public void whenCheckinHealthForModuleListWithNullEntries_thenIgnoreNullEntries() {
		List<ModuleFitness> fitnesses = ModuleFitnessChecker.checkHealth(Arrays.asList(mock(PepperModule.class), null, mock(PepperModule.class)));
		assertThat(fitnesses.size()).isEqualTo(2);
	}

	@Test
	public void whenCheckingFitnessForNull_thenReturnNull() {
		PepperModule module = null;
		assertThat(ModuleFitnessChecker.checkFitness(module)).isNull();
	}

	@Test
	public void whenCheckingFitnessFeatureThrowsException_thenCorrespondingFitnessFeatureShouldBeFalse() {
		PepperModule module = mock(PepperModule.class);
		when(module.getDesc()).thenThrow(new RuntimeException());
		assertThat(ModuleFitnessChecker.checkFitness(module).getFitness(FitnessFeature.HAS_NAME)).isEqualTo(false);
	}

	@Test
	public void whenModuleHasName_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperModule module = mock(PepperModule.class);
		when(module.getName()).thenReturn("MyModule");
		assertThat(ModuleFitnessChecker.checkFitness(module).getFitness(FitnessFeature.HAS_NAME)).isEqualTo(true);
	}

	@Test
	public void whenModuleHasNoName_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperModule module = mock(PepperModule.class);
		when(module.getName()).thenReturn(null);
		assertThat(ModuleFitnessChecker.checkFitness(module).getFitness(FitnessFeature.HAS_NAME)).isEqualTo(false);
	}

	@Test
	public void whenModuleHasDescription_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperModule module = mock(PepperModule.class);
		when(module.getDesc()).thenReturn("any description");
		assertThat(ModuleFitnessChecker.checkFitness(module).getFitness(FitnessFeature.HAS_DESCRIPTION)).isEqualTo(true);
	}

	@Test
	public void whenModuleHasNoDescription_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperModule module = mock(PepperModule.class);
		when(module.getDesc()).thenReturn(null);
		assertThat(ModuleFitnessChecker.checkFitness(module).getFitness(FitnessFeature.HAS_DESCRIPTION)).isEqualTo(false);
	}

	@Test
	public void whenModuleHasSupplierContact_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperModule module = mock(PepperModule.class);
		when(module.getSupplierContact()).thenReturn(URI.createURI("me@mail.com"));
		assertThat(ModuleFitnessChecker.checkFitness(module).getFitness(FitnessFeature.HAS_SUPPLIER_CONTACT)).isEqualTo(true);
	}

	@Test
	public void whenModuleHasNoSupplierContact_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperModule module = mock(PepperModule.class);
		when(module.getSupplierContact()).thenReturn(null);
		assertThat(ModuleFitnessChecker.checkFitness(module).getFitness(FitnessFeature.HAS_SUPPLIER_CONTACT)).isEqualTo(false);
	}

	@Test
	public void whenModuleHasSupplierHomepage_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperModule module = mock(PepperModule.class);
		when(module.getSupplierHomepage()).thenReturn(URI.createURI("http://me.com"));
		assertThat(ModuleFitnessChecker.checkFitness(module).getFitness(FitnessFeature.HAS_SUPPLIER_HP)).isEqualTo(true);
	}

	@Test
	public void whenModuleHasNoSupplierHomepage_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperModule module = mock(PepperModule.class);
		when(module.getSupplierHomepage()).thenReturn(null);
		assertThat(ModuleFitnessChecker.checkFitness(module).getFitness(FitnessFeature.HAS_SUPPLIER_HP)).isEqualTo(false);
	}

	@Test
	public void whenImporterSupportsIsImportable_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperImporter module = mock(PepperImporter.class);
		when(module.isImportable(any(URI.class))).thenReturn(1.0);
		assertThat(ModuleFitnessChecker.checkFitness(module).getFitness(FitnessFeature.IS_IMPORTABLE)).isEqualTo(true);
	}

	@Test
	public void whenImporterDoesNotSupportIsImportable_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperImporter importer = mock(PepperImporter.class);
		when(importer.isImportable(any(URI.class))).thenReturn(null);
		assertThat(ModuleFitnessChecker.checkFitness(importer).getFitness(FitnessFeature.IS_IMPORTABLE)).isEqualTo(false);
	}

	@Test
	public void whenImportereHasFormats_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperImporter importer = mock(PepperImporter.class);
		when(importer.getSupportedFormats()).thenReturn(Arrays.asList(new FormatDesc().setFormatName("anyFormat").setFormatVersion("any Version")));
		assertThat(ModuleFitnessChecker.checkFitness(importer).getFitness(FitnessFeature.HAS_SUPPORTED_FORMATS)).isEqualTo(true);
	}

	@Test
	public void whenImporterHasNoFormats_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperImporter exporter = mock(PepperImporter.class);
		when(exporter.getSupportedFormats()).thenReturn(null);
		assertThat(ModuleFitnessChecker.checkFitness(exporter).getFitness(FitnessFeature.HAS_SUPPORTED_FORMATS)).isEqualTo(false);
		when(exporter.getSupportedFormats()).thenReturn(Arrays.asList(new FormatDesc()));
		assertThat(ModuleFitnessChecker.checkFitness(exporter).getFitness(FitnessFeature.HAS_SUPPORTED_FORMATS)).isEqualTo(false);
	}

	@Test
	public void whenExportereHasFormats_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperExporter module = mock(PepperExporter.class);
		when(module.getSupportedFormats()).thenReturn(Arrays.asList(new FormatDesc().setFormatName("anyFormat").setFormatVersion("any Version")));
		assertThat(ModuleFitnessChecker.checkFitness(module).getFitness(FitnessFeature.HAS_SUPPORTED_FORMATS)).isEqualTo(true);
	}

	@Test
	public void whenExporterHasNoFormats_thenCorrespondingFitnessFeatureShouldBeTrue() {
		PepperExporter exporter = mock(PepperExporter.class);
		when(exporter.getSupportedFormats()).thenReturn(null);
		assertThat(ModuleFitnessChecker.checkFitness(exporter).getFitness(FitnessFeature.HAS_SUPPORTED_FORMATS)).isEqualTo(false);
		when(exporter.getSupportedFormats()).thenReturn(Arrays.asList(new FormatDesc()));
		assertThat(ModuleFitnessChecker.checkFitness(exporter).getFitness(FitnessFeature.HAS_SUPPORTED_FORMATS)).isEqualTo(false);
	}

	@Test
	public void whenCheckingFitnessForNullSet_thenReturnEmptyList() {
		Collection<PepperModule> modules = null;
		ModuleFitnessChecker.checkFitness(modules).isEmpty();
	}

	@Test
	public void whenCheckingFitnessForFitModule_thenReturnFit() {
		PepperModule module = createFitImporter();
		assertThat(ModuleFitnessChecker.checkFitness(module).getOverallFitness()).isEqualTo(Fitness.FIT);
	}

	@Test
	public void whenCheckingFitnessForFitImporter_thenReturnFit() {
		PepperModule module = createFitImporter();
		assertThat(ModuleFitnessChecker.checkFitness(module).getOverallFitness()).isEqualTo(Fitness.FIT);
	}

	@Test
	public void whenCheckingFitnessForMultipleModules_thenReturnListOfFitnessValues() {
		PepperModule fitModule = createFitImporter();
		PepperModule healthyModule = createHealthyModule();
		PepperModule criticalModule = createCriticalModule();

		final List<ModuleFitness> fitnesses = ModuleFitnessChecker.checkFitness(Arrays.asList(fitModule, healthyModule, criticalModule));
		assertThat(fitnesses.get(0).getOverallFitness()).isEqualTo(Fitness.FIT);
		assertThat(fitnesses.get(1).getOverallFitness()).isEqualTo(Fitness.HEALTHY);
		assertThat(fitnesses.get(2).getOverallFitness()).isEqualTo(Fitness.CRITICAL);
	}

	@Test
	public void whenCheckinFitnessForModuleListWithNullEntries_thenIgnoreNullEntries() {
		final List<ModuleFitness> fitnesses = ModuleFitnessChecker.checkFitness(Arrays.asList(mock(PepperModule.class), null, mock(PepperModule.class)));
		assertThat(fitnesses.size()).isEqualTo(2);
	}

	@Test(expected = PepperFWException.class)
	public void whenNoPepperWasSpecified_thenRunITestShouldFail() {
		final PepperImporter importer = mock(PepperImporter.class);
		ModuleFitnessChecker.runITest(importer, null);
	}

	@Test
	public void whenNoPepperModuleWasSpecified_thenReturnNull() {
		final Pepper pepper = mock(Pepper.class);
		assertThat(ModuleFitnessChecker.runITest(null, pepper)).isNull();
	}

	@Test
	public void whenPepperModuleReturnsEmptyTestDesc_thenReturnNull() {
		final Pepper pepper = mock(Pepper.class);
		final PepperImporter importer = mock(PepperImporter.class);
		when(importer.getIntegrationTestDesc()).thenReturn(null);
		assertThat(ModuleFitnessChecker.runITest(importer, pepper)).isNull();
	}

	@Test
	public void whenTestDescIsNotValid_thenReturnFalse() {
		final Pepper pepper = mock(Pepper.class);
		final PepperImporter importer = mock(PepperImporter.class);
		final IntegrationTestDesc desc = mock(IntegrationTestDesc.class);
		when(importer.getIntegrationTestDesc()).thenReturn(desc);
		assertThat(ModuleFitnessChecker.runITest(importer, pepper)).isFalse();
	}
}
