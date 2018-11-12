package org.springframework.samples;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.FatalBeanException;
import org.springframework.expression.EvaluationContext;
import org.springframework.messaging.simp.broker.DefaultSubscriptionRegistry;
import org.springframework.stereotype.Component;

@Component
public class SuperFudgeHelper {

	public static void enhanceWebsocketMessageSelectorSpelContext() {
		try {
			Field messageEvalContextField = DefaultSubscriptionRegistry.class.getDeclaredField("messageEvalContext");
			messageEvalContextField.setAccessible(true);
			EvaluationContext messageEvalContext = (EvaluationContext) messageEvalContextField.get(null);
//			Field methodResolversField = SimpleEvaluationContext.class.getDeclaredField("methodResolvers");
//			methodResolversField.setAccessible(true);
//			List<MethodResolver> methodResolvers = Arrays
//					.asList(DataBindingMethodResolver.forInstanceMethodInvocation());
//			methodResolversField.set(messageEvalContext, methodResolvers);

			messageEvalContext.setVariable("containsAny",
					SuperFudgeHelper.class.getDeclaredMethod("containsAny", List.class, List.class));

		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException
				| NoSuchMethodException e) {
			throw new FatalBeanException(
					"Reflection failed for DefaultSubscriptionRegistry - this might be because Spring Framework has upgraded and has been refactored",
					e);
		}
	}

	public static boolean containsAny(List<String> simpDestinations, List<String> tickers) {
		String simpDestination = simpDestinations.get(0);
		boolean result = tickers.stream().anyMatch(ticker -> simpDestination.endsWith(ticker));

		return result;
	}

	public static void main(String[] args) {
		List<String> d = new ArrayList<String>();
		d.add("/topic/price.stock.MSFT");

		System.out.println(d.stream().anyMatch(p -> p.endsWith("MSFT")));
	}

}
