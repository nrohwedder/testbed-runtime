package de.itm.uniluebeck.tr.wiseml.merger.internals.merge.elements;

import java.util.ArrayList;

import de.itm.uniluebeck.tr.wiseml.merger.config.MergerConfiguration;
import de.itm.uniluebeck.tr.wiseml.merger.internals.WiseMLTag;
import de.itm.uniluebeck.tr.wiseml.merger.internals.merge.MergerResources;
import de.itm.uniluebeck.tr.wiseml.merger.internals.merge.WiseMLElementMerger;
import de.itm.uniluebeck.tr.wiseml.merger.internals.tree.WiseMLTreeReader;

public class WiseMLMerger extends WiseMLElementMerger {
	
	private static final int INIT 			= 0;
	private static final int SETUP 			= 1;
	private static final int SCENARIOS 		= 2;
	//private static final int TRACES 		= 3;
	
	private int state;

	public WiseMLMerger( 
			WiseMLTreeReader[] inputs,
			MergerConfiguration configuration) {
		super(null, inputs, configuration, new MergerResources(), WiseMLTag.wiseml);
		this.state = INIT;
	}

	@Override
	protected void fillQueue() {
		if (state == INIT) {
			mergeSetups(); // <setup> is required
			return;
		}
		if (state == SETUP && mergeScenarioLists()) {
			return;
		}
		if (state == SCENARIOS && mergeTraceLists()) {
			return;
		}
		finished = true;
	}
	
	private void mergeSetups() {
		skipInputsToTag(WiseMLTag.setup);
		queue.add(new SetupMerger(this, getInputSubElementReaders(), null, null));
		state++;
	}
	
	private boolean mergeScenarioLists() {
		for (int i = 0; i < inputs.length; i++) {
			inputs[i].nextSubElementReader();
		}
		
		// select readers with <scenario> tags
		WiseMLTreeReader[] scenarioInputs = getInputElementReadersWithTag(WiseMLTag.scenario);
		
		// add list merger to queue
		queue.add(new ScenarioListMerger(
				this,
				scenarioInputs,
				null,
				null));
		
		// next state
		state++;
		
		return !queue.isEmpty();
	}
	
	private boolean mergeTraceLists() {
		// select readers with <trace> tags
		WiseMLTreeReader[] traceInputs = getInputElementReadersWithTag(WiseMLTag.trace);
		
		// add list merger to queue
		queue.add(new TraceListMerger(
				this,
				traceInputs,
				null,
				null));
		
		// next state
		state++;
		
		return !queue.isEmpty();
	}

	private WiseMLTreeReader[] getInputElementReadersWithTag(final WiseMLTag tag) {
		ArrayList<WiseMLTreeReader> list = new ArrayList<WiseMLTreeReader>();
		
		for (int i = 0; i < inputs.length; i++) {
			if (inputs[i].isMappedToTag()) {
				WiseMLTreeReader nextReader = inputs[i];
				if (tag.equals(nextReader.getTag())) {
					list.add(nextReader);
				}
			}
		}

		return list.toArray(new WiseMLTreeReader[list.size()]);
	}
	
	private WiseMLTreeReader[] getInputSubElementReaders() {
		WiseMLTreeReader[] result = new WiseMLTreeReader[inputs.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = inputs[i].getSubElementReader();
		}
		return result;
	}
	
	private void skipInputsToTag(final WiseMLTag tag) {
		for (int i = 0; i < inputs.length; i++) {
			while (true) {
				if (inputs[i].getSubElementReader() != null 
						&& inputs[i].getSubElementReader().isMappedToTag()) {
					WiseMLTreeReader input = inputs[i].getSubElementReader();
					if (tag.equals(input.getTag())) {
						break;
					}
				}
				//inputs[i] = nextTag(inputs[i]);
				inputs[i].nextSubElementReader();
			}
		}
	}
/*
	private WiseMLTreeReader nextTag(WiseMLTreeReader reader) {
		if (reader == null) {
			return null;
		}
		
		if (reader.isFinished()) {
			return nextTag(reader.getParentReader());
		}
		
		if (reader.nextSubElementReader()) {
			return reader.getSubElementReader();
		}
		
		return nextTag(reader.getParentReader());
	}
	*/
	

	/*
	private WiseMLTreeReader[] getInputSubElementReaders(final WiseMLTag tag) {
		ArrayList<WiseMLTreeReader> list = new ArrayList<WiseMLTreeReader>();
		for (int i = 0; i < inputs.length; i++) {
			if (inputs[i].getSubElementReader() != null) {
				if (inputs[i].getSubElementReader().isMappedToTag()) {
					WiseMLElementReader nextReader = (WiseMLElementReader)inputs[i].getSubElementReader();
					if (tag.equals(nextReader.getTag())) {
						list.add(nextReader);
					}
				}
			}
		}
		return list.toArray(new WiseMLTreeReader[list.size()]);
	}
	*/

}