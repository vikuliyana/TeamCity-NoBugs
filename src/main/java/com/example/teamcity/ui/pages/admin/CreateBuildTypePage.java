package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class CreateBuildTypePage extends CreateBasePage {
    private static final String BUILD_TYPE_SHOW_MODE = "createBuildTypeMenu";

    public SelenideElement buildTypeNameValidationError = $("#error_buildTypeName");

    @Step("Create Build Type page")
    public static CreateBuildTypePage open(String projectId) {
        return Selenide.open(CREATE_URL.formatted(projectId, BUILD_TYPE_SHOW_MODE), CreateBuildTypePage.class);
    }

    @Step("Create form for Build Type")
    public CreateBuildTypePage createForm(String url) {
        baseCreateForm(url);
        return this;
    }

    @Step("Set up Build Type name")
    public void setupBuildType(String buildTypeName) {
        buildTypeNameInput.val(buildTypeName);
        submitButton.click();
    }

    @Step("Set up Build Type without name")
    public CreateBuildTypePage setupBuildTypeWithEmptyName() {
        buildTypeNameInput.clear();
        submitButton.click();
        return this;
    }
}
