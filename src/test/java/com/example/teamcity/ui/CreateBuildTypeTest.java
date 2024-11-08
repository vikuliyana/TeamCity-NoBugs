package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.ui.pages.BuildTypePage;
import com.example.teamcity.ui.pages.ProjectPage;
import com.example.teamcity.ui.pages.ProjectsPage;
import com.example.teamcity.ui.pages.admin.CreateBuildTypePage;
import com.example.teamcity.ui.pages.admin.CreateProjectPage;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Endpoint.PROJECTS;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;

@Test(groups = {"Regression"})
public class CreateBuildTypeTest extends BaseUiTest {
    private static final String REPO_URL = "https://github.com/vikuliyana/TeamCity-NoBugs.git";

    @Test(description = "User should be able to create build configuration", groups = {"Positive"})
    public void userCreatesBuildType() {
        // подготовка окружения
        loginAs(testData.getUser());
        var createdProject = superUserCheckRequests.<Project>getRequest(PROJECTS).create(generate(Project.class));

        // взаимодействие с UI
        CreateBuildTypePage.open(createdProject.getId())
                .createForm(REPO_URL)
                .setupBuildType(testData.getBuildType().getName());

        // проверка состояния API
        // (корректность отправки данных с UI на API)
        var createdBuildType = superUserCheckRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES).read("name:" + testData.getBuildType().getName());
        softy.assertNotNull(createdBuildType);

        // проверка состояния UI
        // (корректность считывания данных и отображение данных на UI)
        BuildTypePage.open(createdBuildType.getId())
                .title.has(Condition.text(testData.getBuildType().getName()));

    }

    @Test(description = "User should not be able to create build configuration with empty name", groups = {"Negative"})
    public void userCreatesProjectWithoutName() {

    }
}
