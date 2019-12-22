package gr.arma3.arma.modarchiver.api.v1;


import gr.arma3.arma.modarchiver.api.v1.interfaces.MetaInfo;
import gr.arma3.arma.modarchiver.api.v1.interfaces.Namespace;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class NS implements Namespace {
	MetaInfo meta;
	String type = "Namespace";
	Class<NS> classRef = NS.class;
}
