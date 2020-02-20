package teamcity.live.reporting;

import io.cucumber.core.feature.FeatureParser;
import io.cucumber.core.gherkin.Container;
import io.cucumber.core.gherkin.Feature;
import io.cucumber.core.gherkin.Node;
import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.EmbedEvent;
import io.cucumber.plugin.event.Event;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.HookTestStep;
import io.cucumber.plugin.event.HookType;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.Result;
import io.cucumber.plugin.event.SnippetsSuggestedEvent;
import io.cucumber.plugin.event.Status;
import io.cucumber.plugin.event.TestCase;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestCaseStarted;
import io.cucumber.plugin.event.TestRunFinished;
import io.cucumber.plugin.event.TestRunStarted;
import io.cucumber.plugin.event.TestSourceRead;
import io.cucumber.plugin.event.TestStep;
import io.cucumber.plugin.event.TestStepFinished;
import io.cucumber.plugin.event.TestStepStarted;
import io.cucumber.plugin.event.WriteEvent;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TeamCityPlugin implements EventListener {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss.SSSZ");

    private static final String TEAMCITY_PREFIX = "##teamcity";

    private static final String TEMPLATE_ENTER_THE_MATRIX = TEAMCITY_PREFIX + "[enteredTheMatrix timestamp = '%s']";
    private static final String TEMPLATE_TEST_RUN_STARTED = TEAMCITY_PREFIX + "[testSuiteStarted timestamp = '%s' name = 'Cucumber']";
    private static final String TEMPLATE_TEST_RUN_FINISHED = TEAMCITY_PREFIX + "[testSuiteFinished timestamp = '%s' name = 'Cucumber']";

    private static final String TEMPLATE_TEST_SUITE_STARTED = TEAMCITY_PREFIX + "[testSuiteStarted timestamp = '%s' locationHint = '%s' name = '%s']";
    private static final String TEMPLATE_TEST_SUITE_FINISHED = TEAMCITY_PREFIX + "[testSuiteFinished timestamp = '%s' name = '%s']";

    private static final String TEMPLATE_TEST_STARTED = TEAMCITY_PREFIX + "[testStarted timestamp = '%s' locationHint = '%s' captureStandardOutput = 'true' name = '%s']";
    private static final String TEMPLATE_TEST_FINISHED = TEAMCITY_PREFIX + "[testFinished timestamp = '%s' duration = '%s' name = '%s']";
    private static final String TEMPLATE_TEST_FAILED = TEAMCITY_PREFIX + "[testFailed timestamp = '%s' duration = '%s' message = '%s' details = '%s' name = '%s']";
    private static final String TEMPLATE_TEST_IGNORED = TEAMCITY_PREFIX + "[testIgnored timestamp = '%s' duration = '%s' message = '%s' name = '%s']";

    private static final String TEMPLATE_PROGRESS_COUNTING_STARTED = TEAMCITY_PREFIX + "[customProgressStatus testsCategory = 'Scenarios' count = '0' timestamp = '%s']";
    private static final String TEMPLATE_PROGRESS_COUNTING_FINISHED = TEAMCITY_PREFIX + "[customProgressStatus testsCategory = '' count = '0' timestamp = '%s']";
    private static final String TEMPLATE_PROGRESS_TEST_STARTED = TEAMCITY_PREFIX + "[customProgressStatus type = 'testStarted' timestamp = '%s']";
    private static final String TEMPLATE_PROGRESS_TEST_FINISHED = TEAMCITY_PREFIX + "[customProgressStatus type = 'testFinished' timestamp = '%s']";

    private static final String TEMPLATE_EMBED_WRITE_EVENT = TEAMCITY_PREFIX + "[message text='%s' status='NORMAL']";

    private static final Pattern ANNOTATION_GLUE_CODE_LOCATION_PATTERN = Pattern.compile("^(.*)\\.(.*)\\([^:]*\\)");
    private static final Pattern LAMBDA_GLUE_CODE_LOCATION_PATTERN = Pattern.compile("^(.*)\\.(.*)\\(.*:.*\\)");

    private final PrintStream out;
    private final List<SnippetsSuggestedEvent> snippets = new ArrayList<>();
    private final Map<URI, Feature> features = new HashMap<>();
    private List<Node> currentStack = new ArrayList<>();

    @SuppressWarnings("unused") // Used by PluginFactory
    public TeamCityPlugin() {
        // This plugin prints markers for Team City and IDEA that allows them
        // associate the output to specific test cases. Printing to system out
        // - and potentially mixing with other formatters - is intentional.
        this(System.out);
    }

    TeamCityPlugin(PrintStream out) {
        this.out = out;
    }

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestRunStarted.class, this::printTestRunStarted);
        publisher.registerHandlerFor(TestCaseStarted.class, this::printTestCaseStarted);
        publisher.registerHandlerFor(TestCaseFinished.class, this::printTestCaseFinished);
        publisher.registerHandlerFor(TestRunFinished.class, this::printTestRunFinished);
    }

    private void printTestRunStarted(TestRunStarted event) {
        System.out.println(String.format("##teamcity[testSuiteStarted name='%s']", "Cucumber"));
    }

    private void printTestRunFinished(TestRunFinished event) {
        System.out.println(String.format("##teamcity[testSuiteFinished name='%s']", "Cucumber"));
    }

    private void printTestCaseStarted(TestCaseStarted event) {
        System.out.println(String.format("##teamcity[testStarted name='%s']", event.getTestCase().getName()));
    }

    private void printTestCaseFinished(TestCaseFinished event) {
        if (!event.getResult().getStatus().equals("PASSED")) {
            Throwable error = event.getResult().getError();
            String details = extractStackTrace(error);
            System.out.println(String.format("##teamcity[testFailed name='%s' message='%s' details='%s']", event.getTestCase().getName(), "Step failed", details));
        }
        System.out.println(String.format("##teamcity[testFinished name='%s']", event.getTestCase().getName()));
    }

    private String extractStackTrace(Throwable error) {
        ByteArrayOutputStream s = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(s);
        error.printStackTrace(printStream);
        return new String(s.toByteArray(), StandardCharsets.UTF_8);
    }
}