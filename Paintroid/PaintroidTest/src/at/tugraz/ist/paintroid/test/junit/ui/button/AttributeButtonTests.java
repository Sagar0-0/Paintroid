package at.tugraz.ist.paintroid.test.junit.ui.button;

import android.test.ActivityInstrumentationTestCase2;
import at.tugraz.ist.paintroid.MainActivity;
import at.tugraz.ist.paintroid.test.junit.stubs.AttributeButtonStubbingAndroidFunctions;
import at.tugraz.ist.paintroid.test.junit.stubs.ToolStub;
import at.tugraz.ist.paintroid.test.junit.stubs.ToolbarStub;
import at.tugraz.ist.paintroid.test.utils.PrivateAccess;
import at.tugraz.ist.paintroid.ui.button.AttributeButton;

public class AttributeButtonTests extends ActivityInstrumentationTestCase2<MainActivity> {

	protected MainActivity activity;
	protected AttributeButtonStubbingAndroidFunctions attributeButton;
	protected ToolbarStub toolbarStub;
	protected ToolStub toolStub;

	public AttributeButtonTests() {
		super("at.tugraz.ist.paintroid", MainActivity.class);
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		activity = this.getActivity();
		attributeButton = new AttributeButtonStubbingAndroidFunctions(activity);
		toolbarStub = new ToolbarStub();
		toolStub = new ToolStub();
		toolbarStub.setReturnValue("getCurrentTool", toolStub);
	}

	public void testSetToolbarShouldAddObservableToTool() {
		attributeButton.setToolbar(toolbarStub);

		assertEquals(1, toolStub.getCallCount("addObserver"));
		assertSame(attributeButton, toolStub.getCall("addObserver", 0).get(0));
	}

	public void testSetToolbarShouldAddObservableToToolbar() {
		attributeButton.setToolbar(toolbarStub);

		assertEquals(1, toolbarStub.getCallCount("addObserver"));
		assertSame(attributeButton, toolbarStub.getCall("addObserver", 0).get(0));
	}

	public void testSetToolbarShouldSetTheBackgroundResourceProvidedByTool() {
		toolStub.setReturnValue("getAttributeButtonResource", 15);

		attributeButton.setToolbar(toolbarStub);

		assertEquals(1, attributeButton.getCallCount("setBackgroundResource"));
		assertEquals(0, attributeButton.getCallCount("setBackgroundColor"));
		assertSame(15, attributeButton.getCall("setBackgroundResource", 0).get(0));
	}

	public void testSetToolbarShouldSetBackgroundColorIfNoBackgroundResourceProvidedByTool() {
		toolStub.setReturnValue("getAttributeButtonColor", 13);

		attributeButton.setToolbar(toolbarStub);

		assertEquals(1, attributeButton.getCallCount("setBackgroundColor"));
		assertEquals(0, attributeButton.getCallCount("setBackgroundResource"));
		assertSame(13, attributeButton.getCall("setBackgroundColor", 0).get(0));
	}

	public void testShouldAddObservableIfToolbarHasNewTool() {
		attributeButton.setToolbar(toolbarStub);
		ToolStub newTool = new ToolStub();
		toolbarStub.setReturnValue("getCurrentTool", newTool);

		attributeButton.update(toolbarStub, null);

		assertEquals(1, newTool.getCallCount("addObserver"));
		assertSame(attributeButton, newTool.getCall("addObserver", 0).get(0));
	}

	public void testShouldSetNewResourceOnUpdate() {
		toolStub.setReturnValue("getAttributeButtonResource", 15);
		attributeButton.setToolbar(toolbarStub);
		toolStub.setReturnValue("getAttributeButtonResource", 14);

		attributeButton.update(toolbarStub, null);

		assertEquals(2, attributeButton.getCallCount("setBackgroundResource"));
		assertEquals(0, attributeButton.getCallCount("setBackgroundColor"));
		assertSame(14, attributeButton.getCall("setBackgroundResource", 1).get(0));
	}

	public void testSetToolbarShouldSetBackgroundColorIfNoBackgroundResourceProvidedByToolOnUpdate() {
		attributeButton.setToolbar(toolbarStub);
		toolStub.setReturnValue("getAttributeButtonColor", 13);

		attributeButton.update(toolbarStub, null);

		assertEquals(2, attributeButton.getCallCount("setBackgroundColor"));
		assertEquals(0, attributeButton.getCallCount("setBackgroundResource"));
		assertSame(13, attributeButton.getCall("setBackgroundColor", 1).get(0));
	}

	public void testShouldDelegateClickEventsToToolWithCorrectButtonNumber() throws SecurityException,
			IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		PrivateAccess.setMemberValue(AttributeButton.class, attributeButton, "buttonNumber", 3);
		attributeButton.setToolbar(toolbarStub);

		attributeButton.onClick(attributeButton);

		assertEquals(1, toolStub.getCallCount("attributeButtonClick"));
		assertSame(3, toolStub.getCall("attributeButtonClick", 0).get(0));
	}

	public void testShouldPassCorrectButtonNumberToGetAttributeButtonResourcer() throws SecurityException,
			IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		PrivateAccess.setMemberValue(AttributeButton.class, attributeButton, "buttonNumber", 3);
		attributeButton.setToolbar(toolbarStub);

		assertEquals(1, toolStub.getCallCount("getAttributeButtonResource"));
		assertSame(3, toolStub.getCall("getAttributeButtonResource", 0).get(0));
	}

	public void testShouldPassCorrectButtonNumberTogetAttributeButtonColor() throws SecurityException,
			IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		PrivateAccess.setMemberValue(AttributeButton.class, attributeButton, "buttonNumber", 3);
		attributeButton.setToolbar(toolbarStub);

		assertEquals(1, toolStub.getCallCount("getAttributeButtonColor"));
		assertSame(3, toolStub.getCall("getAttributeButtonColor", 0).get(0));
	}
}